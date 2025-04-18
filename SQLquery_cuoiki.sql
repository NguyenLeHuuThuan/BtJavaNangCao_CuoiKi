--Xóa dtb nếu như đã tồn tại
IF EXISTS (SELECT * FROM sys.databases WHERE name = N'BTCK')
BEGIN
    -- Đóng tất cả các kết nối đến cơ sở dữ liệu
    EXECUTE sp_MSforeachdb 'IF ''?'' = ''BTCK'' 
    BEGIN 
        DECLARE @sql AS NVARCHAR(MAX) = ''USE [?]; ALTER DATABASE [?] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;''
        EXEC (@sql)
    END'
    -- Xóa tất cả các kết nối tới cơ sở dữ liệu (thực hiện qua hệ thống master)
    USE master;

    -- Xóa cơ sở dữ liệu nếu tồn tại
    DROP DATABASE BTCK;
END
-- Tạo database
CREATE DATABASE BTCK;
GO
USE BTCK;
GO

-- Tạo bảng Quốc Gia
CREATE TABLE QuocGia (
    maQG CHAR(4) PRIMARY KEY,
    tenQG NVARCHAR(100)
);

-- Tạo bảng Tỉnh Thành
CREATE TABLE TinhThanh (
    maTT CHAR(3) PRIMARY KEY,
    QGno CHAR(4) FOREIGN KEY REFERENCES QuocGia(maQG) ON DELETE CASCADE ON UPDATE CASCADE,
    tenTT NVARCHAR(100)
);

-- Tạo bảng Quận Huyện
CREATE TABLE QuanHuyen (
    maQH CHAR(3) PRIMARY KEY,
    TTno CHAR(3) FOREIGN KEY REFERENCES TinhThanh(maTT) ON DELETE CASCADE ON UPDATE CASCADE,
    tenQH NVARCHAR(100)
);

-- Tạo bảng Phường Xã
CREATE TABLE PhuongXa (
    maPX CHAR(3) PRIMARY KEY,
    QHno CHAR(3) FOREIGN KEY REFERENCES QuanHuyen(maQH) ON DELETE CASCADE ON UPDATE CASCADE,
    tenPX NVARCHAR(100)
);

-- Tạo bảng Nhà Cung Cấp
CREATE TABLE NhaCungCap (
    maNCC CHAR(10) PRIMARY KEY,
    tenNCC NVARCHAR(100),
    SDT CHAR(11) unique,
    PXno CHAR(3) FOREIGN KEY REFERENCES PhuongXa(maPX) ON DELETE CASCADE ON UPDATE CASCADE,
    soNhaTenDuong NVARCHAR(100),
	matKhau VARCHAR(100),
	email VARCHAR(50) UNIQUE CHECK (Email LIKE '[a-z]%@%'),
	chuThich nvarchar(100)
);

-- Tạo bảng Phiếu Nhập
CREATE TABLE PhieuNhap (
    maPN CHAR(7) PRIMARY KEY,
    NCCno CHAR(10) FOREIGN KEY REFERENCES NhaCungCap(maNCC) ON DELETE CASCADE ON UPDATE CASCADE,
    ngayNhapHang DATE
);

--Tạo bảng Danh Mục
CREATE TABLE DanhMuc (
   maDM CHAR(7) PRIMARY KEY,
   tenDM NVARCHAR(100),
);

-- Tạo bảng Sản Phẩm
CREATE TABLE SanPham (
    maSP CHAR(7) PRIMARY KEY,
    tenSP NVARCHAR(100),
    donGiaBan MONEY,
    soLuongHienCon BIGINT CHECK (soLuongHienCon >= 0),
	DMno CHAR(7) FOREIGN KEY REFERENCES DanhMuc(maDM) ON DELETE CASCADE ON UPDATE CASCADE,
	linkAnh VARCHAR(300)
);

-- Tạo bảng Chi Tiết Phiếu Nhập
CREATE TABLE ChiTietPhieuNhap (
    maPN CHAR(7) FOREIGN KEY REFERENCES PhieuNhap(maPN) ON DELETE CASCADE ON UPDATE CASCADE,
    maSP CHAR(7) FOREIGN KEY REFERENCES SanPham(maSP) ON DELETE CASCADE ON UPDATE CASCADE,
    soLuongNhap INT,
    giaNhap MONEY,
    PRIMARY KEY (maPN, maSP)
);

-- Tạo bảng Khách Hàng
CREATE TABLE KhachHang (
    maKH CHAR(10) PRIMARY KEY,
    tenKH NVARCHAR(100),
    SDT CHAR(11) UNIQUE,
    Email VARCHAR(50) UNIQUE,
    soDuTaiKhoan MONEY,
    PXno CHAR(3) FOREIGN KEY REFERENCES PhuongXa(maPX) ON DELETE CASCADE ON UPDATE CASCADE,
    soNhaTenDuong NVARCHAR(50),
	matKhau VARCHAR(100),
	gioiTinh nvarchar(5)
);

--Tạo bảng chức vụ
create table ChucVu (
	maCV char(7) primary key,
	tenCV nvarchar(100)
)

-- Tạo bảng Nhân Viên
CREATE TABLE NhanVien (
    maNV CHAR(10) PRIMARY KEY,
    tenNV NVARCHAR(100),
    SDT CHAR(11) UNIQUE,
    Email VARCHAR(50) UNIQUE,
    gioiTinh BIT DEFAULT 1 CHECK (gioiTinh IN (0, 1)),
    DoB DATE CHECK (DATEDIFF(YEAR, DoB, GETDATE()) >= 18),
    Salary MONEY CHECK (Salary >= 0) DEFAULT 5000000,
	ngayVaoLam DATE,
	PXno CHAR(3) FOREIGN KEY REFERENCES PhuongXa(maPX) ON DELETE CASCADE ON UPDATE CASCADE,
    soNhaTenDuong NVARCHAR(50),
	ChucVuno char(7) FOREIGN KEY REFERENCES ChucVu(maCV) ON DELETE CASCADE ON UPDATE CASCADE,
);

-- Tạo bảng Đơn Đặt Hàng - Hóa Đơn
CREATE TABLE DonDatHang_HoaDon (
    maDH CHAR(10) PRIMARY KEY,
    NVno CHAR(10) FOREIGN KEY REFERENCES NhanVien(maNV) ON DELETE CASCADE ON UPDATE CASCADE,
    KHno CHAR(10) FOREIGN KEY REFERENCES KhachHang(maKH) ON DELETE no action ON UPDATE no action,
    ngayTaoDH DATE DEFAULT GETDATE(),
);

-- Tạo bảng Chi Tiết Đơn Hàng
CREATE TABLE ChiTietDonHang (
    maDH CHAR(10) FOREIGN KEY REFERENCES DonDatHang_HoaDon(maDH),
    maSP CHAR(7) FOREIGN KEY REFERENCES SanPham(maSP),
    soLuongDat INT,
    donGia MONEY,
    PRIMARY KEY (maDH, maSP)
);

-- Nhập liệu mẫu
SET DATEFORMAT DMY;


-- =============================================
-- Stored Procedures cho bảng DanhMuc
-- =============================================
go
CREATE PROCEDURE sp_DanhMuc_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maDM, tenDM FROM DanhMuc;
END;
GO

CREATE PROCEDURE sp_DanhMuc_Insert
    @maDM CHAR(7),
    @tenDM NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF EXISTS (SELECT 1 FROM DanhMuc WHERE maDM = @maDM)
            RAISERROR ('Mã danh mục đã tồn tại.', 16, 1);
        ELSE
        BEGIN
            INSERT INTO DanhMuc (maDM, tenDM) VALUES (@maDM, @tenDM);
            SELECT 'Thêm danh mục thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi thêm danh mục: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_DanhMuc_Update
    @maDM CHAR(7),
    @tenDM NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM DanhMuc WHERE maDM = @maDM)
            RAISERROR ('Mã danh mục không tồn tại.', 16, 1);
        ELSE
        BEGIN
            UPDATE DanhMuc SET tenDM = @tenDM WHERE maDM = @maDM;
            SELECT 'Cập nhật danh mục thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi cập nhật danh mục: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_DanhMuc_Delete
    @maDM CHAR(7)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM DanhMuc WHERE maDM = @maDM)
            RAISERROR ('Mã danh mục không tồn tại.', 16, 1);
        ELSE
        BEGIN
            DELETE FROM DanhMuc WHERE maDM = @maDM;
            SELECT 'Xóa danh mục thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi xóa danh mục: %s', 16, 1, '');
    END CATCH
END;
GO

-- =============================================
-- Stored Procedures cho bảng SanPham
-- =============================================

CREATE PROCEDURE sp_SanPham_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maSP, tenSP, donGiaBan, soLuongHienCon, linkAnh FROM SanPham;
END;

GO
CREATE PROCEDURE sp_SanPham_Insert
    @maSP CHAR(7),
    @tenSP NVARCHAR(100),
    @donGiaBan MONEY,
    @soLuongHienCon BIGINT,
    @DMno CHAR(7),
    @linkAnh VARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF EXISTS (SELECT 1 FROM SanPham WHERE maSP = @maSP)
            RAISERROR ('Mã sản phẩm đã tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM DanhMuc WHERE maDM = @DMno)
            RAISERROR ('Mã danh mục không tồn tại.', 16, 1);
        ELSE IF @soLuongHienCon < 0
            RAISERROR ('Số lượng hiện còn phải >= 0.', 16, 1);
        ELSE
        BEGIN
            INSERT INTO SanPham (maSP, tenSP, donGiaBan, soLuongHienCon, DMno, linkAnh)
            VALUES (@maSP, @tenSP, @donGiaBan, @soLuongHienCon, @DMno, @linkAnh);
            SELECT 'Thêm sản phẩm thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi thêm sản phẩm: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_SanPham_Update
    @maSP CHAR(7),
    @tenSP NVARCHAR(100),
    @donGiaBan MONEY,
    @soLuongHienCon BIGINT,
    @DMno CHAR(7),
    @linkAnh VARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM SanPham WHERE maSP = @maSP)
            RAISERROR ('Mã sản phẩm không tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM DanhMuc WHERE maDM = @DMno)
            RAISERROR ('Mã danh mục không tồn tại.', 16, 1);
        ELSE IF @soLuongHienCon < 0
            RAISERROR ('Số lượng hiện còn phải >= 0.', 16, 1);
        ELSE
        BEGIN
            UPDATE SanPham
            SET tenSP = @tenSP, donGiaBan = @donGiaBan, soLuongHienCon = @soLuongHienCon, DMno = @DMno, linkAnh = @linkAnh
            WHERE maSP = @maSP;
            SELECT 'Cập nhật sản phẩm thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi cập nhật sản phẩm: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_SanPham_Delete
    @maSP CHAR(7)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM SanPham WHERE maSP = @maSP)
            RAISERROR ('Mã sản phẩm không tồn tại.', 16, 1);
        ELSE
        BEGIN
            DELETE FROM SanPham WHERE maSP = @maSP;
            SELECT 'Xóa sản phẩm thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi xóa sản phẩm: %s', 16, 1, '');
    END CATCH
END;
GO

-- =============================================
-- Stored Procedures cho bảng NhaCungCap
-- =============================================

CREATE PROCEDURE sp_NhaCungCap_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maNCC, tenNCC, SDT, PXno, soNhaTenDuong, matKhau, email, chuThich FROM NhaCungCap;
END;
GO

CREATE PROCEDURE sp_NhaCungCap_Insert
    @maNCC CHAR(10),
    @tenNCC NVARCHAR(100),
    @SDT CHAR(11),
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(100),
    @matKhau VARCHAR(100),
    @email VARCHAR(50),
    @chuThich NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF EXISTS (SELECT 1 FROM NhaCungCap WHERE maNCC = @maNCC)
            RAISERROR ('Mã nhà cung cấp đã tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM NhaCungCap WHERE SDT = @SDT)
            RAISERROR ('Số điện thoại đã tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM NhaCungCap WHERE email = @email)
            RAISERROR ('Email đã tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM PhuongXa WHERE maPX = @PXno)
            RAISERROR ('Mã phường/xã không tồn tại.', 16, 1);
        ELSE IF @email NOT LIKE '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}'
            RAISERROR ('Email không hợp lệ.', 16, 1);
        ELSE
        BEGIN
            INSERT INTO NhaCungCap (maNCC, tenNCC, SDT, PXno, soNhaTenDuong, matKhau, email, chuThich)
            VALUES (@maNCC, @tenNCC, @SDT, @PXno, @soNhaTenDuong, @matKhau, @email, @chuThich);
            SELECT 'Thêm nhà cung cấp thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi thêm nhà cung cấp: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_NhaCungCap_Update
    @maNCC CHAR(10),
    @tenNCC NVARCHAR(100),
    @SDT CHAR(11),
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(100),
    @matKhau VARCHAR(100),
    @email VARCHAR(50),
    @chuThich NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM NhaCungCap WHERE maNCC = @maNCC)
            RAISERROR ('Mã nhà cung cấp không tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM NhaCungCap WHERE SDT = @SDT AND maNCC != @maNCC)
            RAISERROR ('Số điện thoại đã tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM NhaCungCap WHERE email = @email AND maNCC != @maNCC)
            RAISERROR ('Email đã tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM PhuongXa WHERE maPX = @PXno)
            RAISERROR ('Mã phường/xã không tồn tại.', 16, 1);
        ELSE IF @email NOT LIKE '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}'
            RAISERROR ('Email không hợp lệ.', 16, 1);
        ELSE
        BEGIN
            UPDATE NhaCungCap
            SET tenNCC = @tenNCC, SDT = @SDT, PXno = @PXno, soNhaTenDuong = @soNhaTenDuong,
                matKhau = @matKhau, email = @email, chuThich = @chuThich
            WHERE maNCC = @maNCC;
            SELECT 'Cập nhật nhà cung cấp thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi cập nhật nhà cung cấp: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_NhaCungCap_Delete
    @maNCC CHAR(10)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM NhaCungCap WHERE maNCC = @maNCC)
            RAISERROR ('Mã nhà cung cấp không tồn tại.', 16, 1);
        ELSE
        BEGIN
            DELETE FROM NhaCungCap WHERE maNCC = @maNCC;
            SELECT 'Xóa nhà cung cấp thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi xóa nhà cung cấp: %s', 16, 1, '');
    END CATCH
END;
GO

-- =============================================
-- Stored Procedures cho bảng PhieuNhap
-- =============================================

CREATE PROCEDURE sp_PhieuNhap_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maPN, NCCno, ngayNhapHang FROM PhieuNhap;
END;
GO

CREATE PROCEDURE sp_PhieuNhap_Insert
    @maPN CHAR(7),
    @NCCno CHAR(10),
    @ngayNhapHang DATE
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF EXISTS (SELECT 1 FROM PhieuNhap WHERE maPN = @maPN)
            RAISERROR ('Mã phiếu nhập đã tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM NhaCungCap WHERE maNCC = @NCCno)
            RAISERROR ('Mã nhà cung cấp không tồn tại.', 16, 1);
        ELSE
        BEGIN
            INSERT INTO PhieuNhap (maPN, NCCno, ngayNhapHang)
            VALUES (@maPN, @NCCno, @ngayNhapHang);
            SELECT 'Thêm phiếu nhập thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi thêm phiếu nhập: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_PhieuNhap_Update
    @maPN CHAR(7),
    @NCCno CHAR(10),
    @ngayNhapHang DATE
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM PhieuNhap WHERE maPN = @maPN)
            RAISERROR ('Mã phiếu nhập không tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM NhaCungCap WHERE maNCC = @NCCno)
            RAISERROR ('Mã nhà cung cấp không tồn tại.', 16, 1);
        ELSE
        BEGIN
            UPDATE PhieuNhap
            SET NCCno = @NCCno, ngayNhapHang = @ngayNhapHang
            WHERE maPN = @maPN;
            SELECT 'Cập nhật phiếu nhập thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi cập nhật phiếu nhập: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_PhieuNhap_Delete
    @maPN CHAR(7)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM PhieuNhap WHERE maPN = @maPN)
            RAISERROR ('Mã phiếu nhập không tồn tại.', 16, 1);
        ELSE
        BEGIN
            DELETE FROM PhieuNhap WHERE maPN = @maPN;
            SELECT 'Xóa phiếu nhập thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi xóa phiếu nhập: %s', 16, 1,'');
    END CATCH
END;
GO

-- =============================================
-- Stored Procedures cho bảng ChiTietPhieuNhap
-- =============================================

CREATE PROCEDURE sp_ChiTietPhieuNhap_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maPN, maSP, soLuongNhap, giaNhap FROM ChiTietPhieuNhap;
END;
GO

CREATE PROCEDURE sp_ChiTietPhieuNhap_Insert
    @maPN CHAR(7),
    @maSP CHAR(7),
    @soLuongNhap INT,
    @giaNhap MONEY
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM PhieuNhap WHERE maPN = @maPN)
            RAISERROR ('Mã phiếu nhập không tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM SanPham WHERE maSP = @maSP)
            RAISERROR ('Mã sản phẩm không tồn tại.', 16, 1);
        ELSE IF @soLuongNhap <= 0
            RAISERROR ('Số lượng nhập phải > 0.', 16, 1);
        ELSE IF @giaNhap < 0
            RAISERROR ('Giá nhập phải >= 0.', 16, 1);
        ELSE
        BEGIN
            INSERT INTO ChiTietPhieuNhap (maPN, maSP, soLuongNhap, giaNhap)
            VALUES (@maPN, @maSP, @soLuongNhap, @giaNhap);
            SELECT 'Thêm chi tiết phiếu nhập thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi thêm chi tiết phiếu nhập: %s', 16, 1,'');
    END CATCH
END;
GO

CREATE PROCEDURE sp_ChiTietPhieuNhap_Update
    @maPN CHAR(7),
    @maSP CHAR(7),
    @soLuongNhap INT,
    @giaNhap MONEY
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM ChiTietPhieuNhap WHERE maPN = @maPN AND maSP = @maSP)
            RAISERROR ('Chi tiết phiếu nhập không tồn tại.', 16, 1);
        ELSE IF @soLuongNhap <= 0
            RAISERROR ('Số lượng nhập phải > 0.', 16, 1);
        ELSE IF @giaNhap < 0
            RAISERROR ('Giá nhập phải >= 0.', 16, 1);
        ELSE
        BEGIN
            UPDATE ChiTietPhieuNhap
            SET soLuongNhap = @soLuongNhap, giaNhap = @giaNhap
            WHERE maPN = @maPN AND maSP = @maSP;
            SELECT 'Cập nhật chi tiết phiếu nhập thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi cập nhật chi tiết phiếu nhập: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_ChiTietPhieuNhap_Delete
    @maPN CHAR(7),
    @maSP CHAR(7)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM ChiTietPhieuNhap WHERE maPN = @maPN AND maSP = @maSP)
            RAISERROR ('Chi tiết phiếu nhập không tồn tại.', 16, 1);
        ELSE
        BEGIN
            DELETE FROM ChiTietPhieuNhap WHERE maPN = @maPN AND maSP = @maSP;
            SELECT 'Xóa chi tiết phiếu nhập thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi xóa chi tiết phiếu nhập: %s', 16, 1,'');
    END CATCH
END;
GO

-- =============================================
-- Stored Procedures cho bảng KhachHang
-- =============================================

CREATE PROCEDURE sp_KhachHang_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maKH, tenKH, SDT, Email, soDuTaiKhoan, PXno, soNhaTenDuong, matKhau, gioiTinh FROM KhachHang;
END;
GO

CREATE PROCEDURE sp_KhachHang_Insert
    @maKH CHAR(10),
    @tenKH NVARCHAR(100),
    @SDT CHAR(11),
    @Email VARCHAR(50),
    @soDuTaiKhoan MONEY,
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(50),
    @matKhau VARCHAR(100),
    @gioiTinh NVARCHAR(5)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF EXISTS (SELECT 1 FROM KhachHang WHERE maKH = @maKH)
            RAISERROR ('Mã khách hàng đã tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM KhachHang WHERE SDT = @SDT)
            RAISERROR ('Số điện thoại đã tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM KhachHang WHERE Email = @Email)
            RAISERROR ('Email đã tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM PhuongXa WHERE maPX = @PXno)
            RAISERROR ('Mã phường/xã không tồn tại.', 16, 1);
        ELSE
        BEGIN
            INSERT INTO KhachHang (maKH, tenKH, SDT, Email, soDuTaiKhoan, PXno, soNhaTenDuong, matKhau, gioiTinh)
            VALUES (@maKH, @tenKH, @SDT, @Email, @soDuTaiKhoan, @PXno, @soNhaTenDuong, @matKhau, @gioiTinh);
            SELECT 'Thêm khách hàng thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi thêm khách hàng: %s', 16, 1,'');
    END CATCH
END;
GO

CREATE PROCEDURE sp_KhachHang_Update
    @maKH CHAR(10),
    @tenKH NVARCHAR(100),
    @SDT CHAR(11),
    @Email VARCHAR(50),
    @soDuTaiKhoan MONEY,
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(50),
    @matKhau VARCHAR(100),
    @gioiTinh NVARCHAR(5)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM KhachHang WHERE maKH = @maKH)
            RAISERROR ('Mã khách hàng không tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM KhachHang WHERE SDT = @SDT AND maKH != @maKH)
            RAISERROR ('Số điện thoại đã tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM KhachHang WHERE Email = @Email AND maKH != @maKH)
            RAISERROR ('Email đã tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM PhuongXa WHERE maPX = @PXno)
            RAISERROR ('Mã phường/xã không tồn tại.', 16, 1);
        ELSE
        BEGIN
            UPDATE KhachHang
            SET tenKH = @tenKH, SDT = @SDT, Email = @Email, soDuTaiKhoan = @soDuTaiKhoan,
                PXno = @PXno, soNhaTenDuong = @soNhaTenDuong, matKhau = @matKhau, gioiTinh = @gioiTinh
            WHERE maKH = @maKH;
            SELECT 'Cập nhật khách hàng thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi cập nhật khách hàng: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_KhachHang_Delete
    @maKH CHAR(10)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM KhachHang WHERE maKH = @maKH)
            RAISERROR ('Mã khách hàng không tồn tại.', 16, 1);
        ELSE
        BEGIN
            DELETE FROM KhachHang WHERE maKH = @maKH;
            SELECT 'Xóa khách hàng thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi xóa khách hàng: %s', 16, 1, '');
    END CATCH
END;
GO

-- =============================================
-- Stored Procedures cho bảng NhanVien
-- =============================================

CREATE PROCEDURE sp_NhanVien_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maNV, tenNV, SDT, Email, gioiTinh, DoB, Salary, ngayVaoLam, PXno, soNhaTenDuong, ChucVuno FROM NhanVien;
END;
GO

CREATE PROCEDURE sp_NhanVien_Insert
    @maNV CHAR(10),
    @tenNV NVARCHAR(100),
    @SDT CHAR(11),
    @Email VARCHAR(50),
    @gioiTinh BIT,
    @DoB DATE,
    @Salary MONEY,
    @ngayVaoLam DATE,
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(50),
    @ChucVuno CHAR(7)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF EXISTS (SELECT 1 FROM NhanVien WHERE maNV = @maNV)
            RAISERROR ('Mã nhân viên đã tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM NhanVien WHERE SDT = @SDT)
            RAISERROR ('Số điện thoại đã tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM NhanVien WHERE Email = @Email)
            RAISERROR ('Email đã tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM PhuongXa WHERE maPX = @PXno)
            RAISERROR ('Mã phường/xã không tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM ChucVu WHERE maCV = @ChucVuno)
            RAISERROR ('Mã chức vụ không tồn tại.', 16, 1);
        ELSE IF DATEDIFF(YEAR, @DoB, GETDATE()) < 18
            RAISERROR ('Nhân viên phải >= 18 tuổi.', 16, 1);
        ELSE IF @Salary < 0
            RAISERROR ('Lương phải >= 0.', 16, 1);
        ELSE
        BEGIN
            INSERT INTO NhanVien (maNV, tenNV, SDT, Email, gioiTinh, DoB, Salary, ngayVaoLam, PXno, soNhaTenDuong, ChucVuno)
            VALUES (@maNV, @tenNV, @SDT, @Email, @gioiTinh, @DoB, @Salary, @ngayVaoLam, @PXno, @soNhaTenDuong, @ChucVuno);
            SELECT 'Thêm nhân viên thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi thêm nhân viên: %s', 16, 1,'');
    END CATCH
END;
GO

CREATE PROCEDURE sp_NhanVien_Update
    @maNV CHAR(10),
    @tenNV NVARCHAR(100),
    @SDT CHAR(11),
    @Email VARCHAR(50),
    @gioiTinh BIT,
    @DoB DATE,
    @Salary MONEY,
    @ngayVaoLam DATE,
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(50),
    @ChucVuno CHAR(7)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM NhanVien WHERE maNV = @maNV)
            RAISERROR ('Mã nhân viên không tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM NhanVien WHERE SDT = @SDT AND maNV != @maNV)
            RAISERROR ('Số điện thoại đã tồn tại.', 16, 1);
        ELSE IF EXISTS (SELECT 1 FROM NhanVien WHERE Email = @Email AND maNV != @maNV)
            RAISERROR ('Email đã tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM PhuongXa WHERE maPX = @PXno)
            RAISERROR ('Mã phường/xã không tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM ChucVu WHERE maCV = @ChucVuno)
            RAISERROR ('Mã chức vụ không tồn tại.', 16, 1);
        ELSE IF DATEDIFF(YEAR, @DoB, GETDATE()) < 18
            RAISERROR ('Nhân viên phải >= 18 tuổi.', 16, 1);
        ELSE IF @Salary < 0
            RAISERROR ('Lương phải >= 0.', 16, 1);
        ELSE
        BEGIN
            UPDATE NhanVien
            SET tenNV = @tenNV, SDT = @SDT, Email = @Email, gioiTinh = @gioiTinh, DoB = @DoB,
                Salary = @Salary, ngayVaoLam = @ngayVaoLam, PXno = @PXno, soNhaTenDuong = @soNhaTenDuong, ChucVuno = @ChucVuno
            WHERE maNV = @maNV;
            SELECT 'Cập nhật nhân viên thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi cập nhật nhân viên: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_NhanVien_Delete
    @maNV CHAR(10)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM NhanVien WHERE maNV = @maNV)
            RAISERROR ('Mã nhân viên không tồn tại.', 16, 1);
        ELSE
        BEGIN
            DELETE FROM NhanVien WHERE maNV = @maNV;
            SELECT 'Xóa nhân viên thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi xóa nhân viên: %s', 16, 1, '');
    END CATCH
END;
GO

-- =============================================
-- Stored Procedures cho bảng DonDatHang_HoaDon
-- =============================================

CREATE PROCEDURE sp_DonDatHang_HoaDon_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maDH, NVno, KHno, ngayTaoDH FROM DonDatHang_HoaDon;
END;
GO

CREATE PROCEDURE sp_DonDatHang_HoaDon_Insert
    @maDH CHAR(10),
    @NVno CHAR(10),
    @KHno CHAR(10),
    @ngayTaoDH DATE
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF EXISTS (SELECT 1 FROM DonDatHang_HoaDon WHERE maDH = @maDH)
            RAISERROR ('Mã đơn đặt hàng đã tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM NhanVien WHERE maNV = @NVno)
            RAISERROR ('Mã nhân viên không tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM KhachHang WHERE maKH = @KHno)
            RAISERROR ('Mã khách hàng không tồn tại.', 16, 1);
        ELSE
        BEGIN
            INSERT INTO DonDatHang_HoaDon (maDH, NVno, KHno, ngayTaoDH)
            VALUES (@maDH, @NVno, @KHno, @ngayTaoDH);
            SELECT 'Thêm đơn đặt hàng thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi thêm đơn đặt hàng: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_DonDatHang_HoaDon_Update
    @maDH CHAR(10),
    @NVno CHAR(10),
    @KHno CHAR(10),
    @ngayTaoDH DATE
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM DonDatHang_HoaDon WHERE maDH = @maDH)
            RAISERROR ('Mã đơn đặt hàng không tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM NhanVien WHERE maNV = @NVno)
            RAISERROR ('Mã nhân viên không tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM KhachHang WHERE maKH = @KHno)
            RAISERROR ('Mã khách hàng không tồn tại.', 16, 1);
        ELSE
        BEGIN
            UPDATE DonDatHang_HoaDon
            SET NVno = @NVno, KHno = @KHno, ngayTaoDH = @ngayTaoDH
            WHERE maDH = @maDH;
            SELECT 'Cập nhật đơn đặt hàng thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi cập nhật đơn đặt hàng: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_DonDatHang_HoaDon_Delete
    @maDH CHAR(10)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM DonDatHang_HoaDon WHERE maDH = @maDH)
            RAISERROR ('Mã đơn đặt hàng không tồn tại.', 16, 1);
        ELSE
        BEGIN
            DELETE FROM DonDatHang_HoaDon WHERE maDH = @maDH;
            SELECT 'Xóa đơn đặt hàng thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi xóa đơn đặt hàng: %s', 16, 1,'');
    END CATCH
END;
GO

-- =============================================
-- Stored Procedures cho bảng ChiTietDonHang
-- =============================================

CREATE PROCEDURE sp_ChiTietDonHang_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maDH, maSP, soLuongDat, donGia FROM ChiTietDonHang;
END;
GO

CREATE PROCEDURE sp_ChiTietDonHang_Insert
    @maDH CHAR(10),
    @maSP CHAR(7),
    @soLuongDat INT,
    @donGia MONEY
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM DonDatHang_HoaDon WHERE maDH = @maDH)
            RAISERROR ('Mã đơn đặt hàng không tồn tại.', 16, 1);
        ELSE IF NOT EXISTS (SELECT 1 FROM SanPham WHERE maSP = @maSP)
            RAISERROR ('Mã sản phẩm không tồn tại.', 16, 1);
        ELSE IF @soLuongDat <= 0
            RAISERROR ('Số lượng đặt phải > 0.', 16, 1);
        ELSE IF @donGia < 0
            RAISERROR ('Đơn giá phải >= 0.', 16, 1);
        ELSE
        BEGIN
            INSERT INTO ChiTietDonHang (maDH, maSP, soLuongDat, donGia)
            VALUES (@maDH, @maSP, @soLuongDat, @donGia);
            SELECT 'Thêm chi tiết đơn hàng thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi thêm chi tiết đơn hàng: %s', 16, 1, '');
    END CATCH
END;
GO

CREATE PROCEDURE sp_ChiTietDonHang_Update
    @maDH CHAR(10),
    @maSP CHAR(7),
    @soLuongDat INT,
    @donGia MONEY
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM ChiTietDonHang WHERE maDH = @maDH AND maSP = @maSP)
            RAISERROR ('Chi tiết đơn hàng không tồn tại.',  16, 1);
        ELSE IF @soLuongDat <= 0
            RAISERROR ('Số lượng đặt phải > 0.', 16, 1);
        ELSE IF @donGia < 0
            RAISERROR ('Đơn giá phải >= 0.', 16, 1);
        ELSE
        BEGIN
            UPDATE ChiTietDonHang
            SET soLuongDat = @soLuongDat, donGia = @donGia
            WHERE maDH = @maDH AND maSP = @maSP;
            SELECT 'Cập nhật chi tiết đơn hàng thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi cập nhật chi tiết đơn hàng: %s', 16, 1,'');
    END CATCH
END;
GO

CREATE PROCEDURE sp_ChiTietDonHang_Delete
    @maDH CHAR(10),
    @maSP CHAR(7)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM ChiTietDonHang WHERE maDH = @maDH AND maSP = @maSP)
            RAISERROR ('Chi tiết đơn hàng không tồn tại.', 16, 1);
        ELSE
        BEGIN
            DELETE FROM ChiTietDonHang WHERE maDH = @maDH AND maSP = @maSP;
            SELECT 'Xóa chi tiết đơn hàng thành công.' AS Message;
        END
    END TRY
    BEGIN CATCH
        RAISERROR ('Lỗi khi xóa chi tiết đơn hàng: %s', 16, 1,'');
    END CATCH
END;
GO
-- Trigger cho bảng ChiTietDonHang: Giảm số lượng hiện còn khi thêm chi tiết đơn hàng
CREATE TRIGGER tr_ChiTietDonHang_Insert
ON ChiTietDonHang
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        -- Kiểm tra và cập nhật số lượng hiện còn
        DECLARE @maSP CHAR(7), @soLuongDat INT, @soLuongHienCon BIGINT;

        -- Lấy thông tin từ bản ghi vừa thêm
        SELECT @maSP = maSP, @soLuongDat = soLuongDat
        FROM inserted;

        -- Lấy số lượng hiện còn của sản phẩm
        SELECT @soLuongHienCon = soLuongHienCon
        FROM SanPham
        WHERE maSP = @maSP;

        -- Kiểm tra sản phẩm tồn tại
        IF @soLuongHienCon IS NULL
        BEGIN
            RAISERROR ('Mã sản phẩm %s không tồn tại.', 16, 1, @maSP);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Kiểm tra số lượng hiện còn đủ để trừ
        IF @soLuongHienCon < @soLuongDat
        BEGIN
            RAISERROR ('Số lượng hiện còn (%d) không đủ để đặt %d sản phẩm %s.', 16, 1, @soLuongHienCon, @soLuongDat, @maSP);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Cập nhật số lượng hiện còn
        UPDATE SanPham
        SET soLuongHienCon = soLuongHienCon - @soLuongDat
        WHERE maSP = @maSP;
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR ('Lỗi khi cập nhật số lượng hiện còn: %s', 16, 1, @ErrorMessage);
        ROLLBACK TRANSACTION;
    END CATCH
END;
GO

-- Trigger cho bảng ChiTietPhieuNhap: Tăng số lượng hiện còn khi thêm chi tiết phiếu nhập
CREATE TRIGGER tr_ChiTietPhieuNhap_Insert
ON ChiTietPhieuNhap
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        -- Kiểm tra và cập nhật số lượng hiện còn
        DECLARE @maSP CHAR(7), @soLuongNhap INT;

        -- Lấy thông tin từ bản ghi vừa thêm
        SELECT @maSP = maSP, @soLuongNhap = soLuongNhap
        FROM inserted;

        -- Kiểm tra sản phẩm tồn tại
        IF NOT EXISTS (SELECT 1 FROM SanPham WHERE maSP = @maSP)
        BEGIN
            RAISERROR ('Mã sản phẩm %s không tồn tại.', 16, 1, @maSP);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Cập nhật số lượng hiện còn
        UPDATE SanPham
        SET soLuongHienCon = soLuongHienCon + @soLuongNhap
        WHERE maSP = @maSP;
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR ('Lỗi khi cập nhật số lượng hiện còn: %s', 16, 1, @ErrorMessage);
        ROLLBACK TRANSACTION;
    END CATCH
END;
GO
-- Trigger cho bảng ChiTietDonHang: Xử lý sửa (UPDATE)
CREATE TRIGGER tr_ChiTietDonHang_Update
ON ChiTietDonHang
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DECLARE @maSP CHAR(7), @soLuongDatCu INT, @soLuongDatMoi INT, @soLuongHienCon BIGINT;

        -- Lấy thông tin từ bản ghi cũ (deleted) và mới (inserted)
        SELECT @maSP = i.maSP, @soLuongDatMoi = i.soLuongDat, @soLuongDatCu = d.soLuongDat
        FROM inserted i
        JOIN deleted d ON i.maDH = d.maDH AND i.maSP = d.maSP;

        -- Lấy số lượng hiện còn của sản phẩm
        SELECT @soLuongHienCon = soLuongHienCon
        FROM SanPham
        WHERE maSP = @maSP;

        -- Kiểm tra sản phẩm tồn tại
        IF @soLuongHienCon IS NULL
        BEGIN
            RAISERROR ('Mã sản phẩm %s không tồnshare tồn tại.', 16, 1, @maSP);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Tính số lượng cần điều chỉnh
        DECLARE @delta INT = @soLuongDatMoi - @soLuongDatCu;

        -- Kiểm tra số lượng hiện còn đủ để trừ thêm (nếu số lượng đặt tăng)
        IF @delta > 0 AND @soLuongHienCon < @delta
        BEGIN
            RAISERROR ('Số lượng hiện còn (%d) không đủ để đặt thêm %d sản phẩm %s.', 16, 1, @soLuongHienCon, @delta, @maSP);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Cập nhật số lượng hiện còn
        UPDATE SanPham
        SET soLuongHienCon = soLuongHienCon - @delta
        WHERE maSP = @maSP;
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR ('Lỗi khi cập nhật số lượng hiện còn: %s', 16, 1, @ErrorMessage);
        ROLLBACK TRANSACTION;
    END CATCH
END;
GO

-- Trigger cho bảng ChiTietDonHang: Xử lý xóa (DELETE)
CREATE TRIGGER tr_ChiTietDonHang_Delete
ON ChiTietDonHang
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DECLARE @maSP CHAR(7), @soLuongDat INT;

        -- Lấy thông tin từ bản ghi bị xóa
        SELECT @maSP = maSP, @soLuongDat = soLuongDat
        FROM deleted;

        -- Kiểm tra sản phẩm tồn tại
        IF NOT EXISTS (SELECT 1 FROM SanPham WHERE maSP = @maSP)
        BEGIN
            RAISERROR ('Mã sản phẩm %s không tồn tại.', 16, 1, @maSP);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Hoàn lại số lượng hiện còn
        UPDATE SanPham
        SET soLuongHienCon = soLuongHienCon + @soLuongDat
        WHERE maSP = @maSP;
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR ('Lỗi khi cập nhật số lượng hiện còn: %s', 16, 1, @ErrorMessage);
        ROLLBACK TRANSACTION;
    END CATCH
END;
GO

-- Trigger cho bảng ChiTietPhieuNhap: Xử lý sửa (UPDATE)
CREATE TRIGGER tr_ChiTietPhieuNhap_Update
ON ChiTietPhieuNhap
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DECLARE @maSP CHAR(7), @soLuongNhapCu INT, @soLuongNhapMoi INT;

        -- Lấy thông tin từ bản ghi cũ (deleted) và mới (inserted)
        SELECT @maSP = i.maSP, @soLuongNhapMoi = i.soLuongNhap, @soLuongNhapCu = d.soLuongNhap
        FROM inserted i
        JOIN deleted d ON i.maPN = d.maPN AND i.maSP = d.maSP;

        -- Kiểm tra sản phẩm tồn tại
        IF NOT EXISTS (SELECT 1 FROM SanPham WHERE maSP = @maSP)
        BEGIN
            RAISERROR ('Mã sản phẩm %s không tồn tại.', 16, 1, @maSP);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Tính số lượng cần điều chỉnh
        DECLARE @delta INT = @soLuongNhapMoi - @soLuongNhapCu;

        -- Cập nhật số lượng hiện còn
        UPDATE SanPham
        SET soLuongHienCon = soLuongHienCon + @delta
        WHERE maSP = @maSP;
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR ('Lỗi khi cập nhật số lượng hiện còn: %s', 16, 1, @ErrorMessage);
        ROLLBACK TRANSACTION;
    END CATCH
END;
GO

-- Trigger cho bảng ChiTietPhieuNhap: Xử lý xóa (DELETE)
CREATE TRIGGER tr_ChiTietPhieuNhap_Delete
ON ChiTietPhieuNhap
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DECLARE @maSP CHAR(7), @soLuongNhap INT, @soLuongHienCon BIGINT;

        -- Lấy thông tin từ bản ghi bị xóa
        SELECT @maSP = maSP, @soLuongNhap = soLuongNhap
        FROM deleted;

        -- Lấy số lượng hiện còn
        SELECT @soLuongHienCon = soLuongHienCon
        FROM SanPham
        WHERE maSP = @maSP;

        -- Kiểm tra sản phẩm tồn tại
        IF @soLuongHienCon IS NULL
        BEGIN
            RAISERROR ('Mã sản phẩm %s không tồn tại.', 16, 1, @maSP);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Kiểm tra số lượng hiện còn đủ để trừ
        IF @soLuongHienCon < @soLuongNhap
        BEGIN
            RAISERROR ('Số lượng hiện còn (%d) không đủ để hoàn %d sản phẩm %s.', 16, 1, @soLuongHienCon, @soLuongNhap, @maSP);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Hoàn lại số lượng hiện còn
        UPDATE SanPham
        SET soLuongHienCon = soLuongHienCon - @soLuongNhap
        WHERE maSP = @maSP;
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR ('Lỗi khi cập nhật số lượng hiện còn: %s', 16, 1, @ErrorMessage);
        ROLLBACK TRANSACTION;
    END CATCH
END;
GO


-- Định dạng ngày là DMY
SET DATEFORMAT DMY;
GO

-- 1. Chèn dữ liệu vào bảng QuocGia
INSERT INTO QuocGia (maQG, tenQG)
VALUES 
    ('VN', N'Việt Nam'),
    ('US', N'Hoa Kỳ'),
    ('JP', N'Nhật Bản');
GO

-- 2. Chèn dữ liệu vào bảng TinhThanh
INSERT INTO TinhThanh (maTT, QGno, tenTT)
VALUES 
    ('HCM', 'VN', N'Thành phố Hồ Chí Minh'),
    ('HN', 'VN', N'Hà Nội'),
    ('DN', 'VN', N'Đà Nẵng');
GO

-- 3. Chèn dữ liệu vào bảng QuanHuyen
INSERT INTO QuanHuyen (maQH, TTno, tenQH)
VALUES 
    ('Q1', 'HCM', N'Quận 1'),
    ('Q7', 'HCM', N'Quận 7'),
    ('BD', 'HN', N'Quận Ba Đình'),
    ('HP', 'DN', N'Quận Hải Châu');
GO

-- 4. Chèn dữ liệu vào bảng PhuongXa
INSERT INTO PhuongXa (maPX, QHno, tenPX)
VALUES 
    ('BTD', 'Q1', N'Phường Bến Thành'),
    ('PMH', 'Q7', N'Phường Phú Mỹ Hưng'),
    ('TH', 'BD', N'Phường Trúc Bạch'),
    ('TB', 'HP', N'Phường Thanh Bình');
GO

-- 5. Chèn dữ liệu vào bảng NhaCungCap
INSERT INTO NhaCungCap (maNCC, tenNCC, SDT, PXno, soNhaTenDuong, matKhau, email, chuThich)
VALUES 
    ('NCC0000001', N'Công ty TNHH Nguyên Liệu Sạch', '09012345678', 'BTD', N'123 Lê Lợi', 'password123', 'nguyenlieu@gmail.com', N'Cung cấp thực phẩm sạch'),
    ('NCC0000002', N'Công ty CP Nông Sản Việt', '09123456789', 'PMH', N'45 Nguyễn Thị Minh Khai', 'pass456', 'nongsanviet@gmail.com', N'Cung cấp rau củ'),
    ('NCC0000003', N'Công ty TNHH Đồ Uống Á Châu', '09234567890', 'TH', N'78 Hàng Bông', 'secure789', 'douongachau@gmail.com', N'Cung cấp đồ uống');
GO

-- 6. Chèn dữ liệu vào bảng DanhMuc
INSERT INTO DanhMuc (maDM, tenDM)
VALUES 
    ('DM00001', N'Quần áo'),
    ('DM00002', N'Giày dép'),
    ('DM00003', N'Đồng hồ')

GO
-- 7. Chèn dữ liệu vào bảng SanPham
INSERT INTO SanPham (maSP, tenSP, donGiaBan, soLuongHienCon, DMno, linkAnh)
VALUES 
    ('SP00001', N'Giày thể thao ASIA', 9999000, 100, 'DM00002', 'https://tse4.mm.bing.net/th?id=OIP.06R0FiqpbtkqLKWRtwmhlAHaHa&pid=Api&P=0&h=180'),
    ('SP00002', N'Giày thể thao ADIDAS', 1899000, 80, 'DM00002', 'https://cf.shopee.vn/file/66fc637c6e4743cccd25ab3b33cb3136'),
    ('SP00003', N'Giày đá bóng ADIDAS', 21000000, 50, 'DM00002', 'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2023/01/giay-da-bong-adidas-x-speedportal-3-multi-ground-boots-gw8478-mau-xanh-la-cay-size-37-63d7730d193b3-30012023143437.jpg'),
    ('SP00004', N'Giày MLB Chunky Liner', 2999000, 60, 'DM00002', 'https://down-vn.img.susercontent.com/file/sg-11134201-22120-mdbwp9w0s1kvc9'),
	('SP00005',N'Giày Nike Air Force',3999000,50,'DM00002','https://capvirgo.com/wp-content/uploads/2022/10/wsxc1664359539205_0.jpg'),
	('SP00006',N'Giày Gucci',3999000,80,'DM00002','https://giaydino.com/wp-content/uploads/2021/11/Giay-Gucci-Son-Tung-hang-Sieu-Cap-scaled.jpg'),
	('SP00007',N'Đồng hồ KONIG74',3999000,50,'DM00003','https://i.ex-cdn.com/nhadautu.vn/files/content/2022/05/10/2-1609.jpg'),
	('SP00008',N'Đồng hồ Mathey Tissot',4500000,100,'DM00003','https://i.ex-cdn.com/nhadautu.vn/files/content/2022/05/10/3-1609.jpg'),
	('SP00009',N'Đồng hồ Fendi',4999000,70,'DM00003','https://i.ex-cdn.com/nhadautu.vn/files/content/2022/05/10/4-1609.jpg'),
	('SP00010',N'Đồng hồ Movado 1881',3999000,50,'DM00003','https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/473941nuW/dong-ho-movado-1881-gia-bao-nhieu-top-mau-dong-ho-7-800x450.jpg'),
	('SP00011',N'Đồng hồ Orient FEU0A001TH ',3999000,50,'DM00003','https://cdn.chiaki.vn/unsafe/0x480/left/top/smart/filters:quality(75)/https://chiaki.vn/upload/product/2016/08/dong-ho-orient-feu0a001th-cho-nam-0-16082016150506.jpg'),
	('SP00012',N'Đồng hồ Timex dây da T2N693J',2095000,50,'DM00003','https://cdn.chiaki.vn/unsafe/0x480/left/top/smart/filters:quality(75)/https://chiaki.vn/upload/product/2016/05/dong-ho-timex-day-da-t2n693-cho-nam2-09052016144616.jpg'),
	('SP00013',N'Đồng hồ CK dây da K2G271C6',7150000,80,'DM00003','https://cdn.chiaki.vn/unsafe/0x480/left/top/smart/filters:quality(75)/https://chiaki.vn/upload/product-gallery/3926/dong-ho-ck-day-da-k2g271c6-nam-tinh-lich-lam-cho-nam-17063244273926.jpg'),
	('SP00014',N'Áo Phông Dior Relaxed-Fit',1980000,50,'DM00001','https://admin.vuahanghieu.com/upload/product/2023/01/ao-phong-dior-relaxed-fit-in-black-logo-embroidered-293j659a0554-mau-den-63bce31067c0e-10012023110120.jpg'),
	('SP00015',N'Bộ quần áo Dior',3999000,50,'DM00001','https://vn-test-11.slatic.net/p/f58731e040048f88b5e8f5c80c6f296a.jpg'),
	('SP00016',N'Áo sơ mi bò LV',3999000,60,'DM00001','https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2023/06/ao-so-mi-nam-louis-vuitton-lv-rainbow-monogram-short-sleeved-denim-shirt-mau-xanh-size-xs-649145815d56b-20062023132153.jpg'),
	('SP00017',N'Áo thun polo',999000,100,'DM00001','https://vn-live-01.slatic.net/p/53be612195430942d28a7488c6a3b38f.jpg'),
	('SP00018',N'Áo phông Gucci',9999000,50,'DM00001','https://vn-live-01.slatic.net/p/53be612195430942d28a7488c6a3b38f.jpg'),
	('SP00019',N'Áo polo Louis Vuitton',2500000,60,'DM00001','https://hotgirlshop.com/uploads/picture/07022022/News/2027215755-ao-thun-lv-chinh-hang.jpg'),
	('SP00020',N'Áo khoác nam Louis Vuitton',5999000,40,'DM00001','https://s1.storage.5giay.vn/image/2019/12/20191228_63682a8c0b146ac16c05286ce506bbcc_1577508005.jpeg')
GO

-- 8. Chèn dữ liệu vào bảng KhachHang
INSERT INTO KhachHang (maKH, tenKH, SDT, Email, soDuTaiKhoan, PXno, soNhaTenDuong, matKhau, gioiTinh)
VALUES 
    ('KH00000001', N'Nguyễn Văn An', '09312345678', 'an.nguyen@gmail.com', 500000, 'BTD', N'56 Lê Lợi', 'khpass123', N'Nam'),
    ('KH00000002', N'Trần Thị Bình', '09423456789', 'binh.tran@gmail.com', 300000, 'PMH', N'12 Nguyễn Huệ', 'khpass456', N'Nữ'),
    ('KH00000003', N'Lê Minh Châu', '09534567890', 'chau.le@gmail.com', 200000, 'TH', N'34 Hàng Bạc', 'khpass789', N'Nữ');
GO

-- 9. Chèn dữ liệu vào bảng ChucVu
INSERT INTO ChucVu (maCV, tenCV)
VALUES 
    ('CV00001', N'Quản lý'),
    ('CV00002', N'Nhân viên bán hàng'),
    ('CV00003', N'Nhân viên kho');
GO

-- 10. Chèn dữ liệu vào bảng NhanVien
INSERT INTO NhanVien (maNV, tenNV, SDT, Email, gioiTinh, DoB, Salary, ngayVaoLam, PXno, soNhaTenDuong, ChucVuno)
VALUES 
    ('NV00000001', N'Phạm Quốc Đạt', '09612345678', 'dat.pham@gmail.com', 1, '15/05/1995', 10000000, '01/01/2023', 'BTD', N'78 Lê Lợi', 'CV00001'),
    ('NV00000002', N'Nguyễn Thị Hồng', '09723456789', 'hong.nguyen@gmail.com', 0, '22/07/1998', 6000000, '01/06/2023', 'PMH', N'23 Nguyễn Huệ', 'CV00002'),
    ('NV00000003', N'Trần Văn Khang', '09834567890', 'khang.tran@gmail.com', 1, '10/03/1996', 5500000, '01/09/2023', 'TB', N'45 Nguyễn Trãi', 'CV00003');
GO

select * from DanhMuc
select * from SanPham