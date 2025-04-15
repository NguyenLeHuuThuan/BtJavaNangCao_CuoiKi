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
	linkAnh VARCHAR(100)
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
go
-- Add NhaCungCap
CREATE PROCEDURE usp_NhaCungCap_Insert (
    @maNCC CHAR(10),
    @tenNCC NVARCHAR(100),
    @SDT CHAR(11),
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(100),
    @matKhau VARCHAR(100), -- HASH THIS IN APPLICATION BEFORE SENDING!
    @email VARCHAR(50),
    @chuThich NVARCHAR(100) = NULL
)
AS
BEGIN
    SET NOCOUNT ON;
    -- Add validation/error handling as needed
    INSERT INTO dbo.NhaCungCap (maNCC, tenNCC, SDT, PXno, soNhaTenDuong, matKhau, email, chuThich)
    VALUES (@maNCC, @tenNCC, @SDT, @PXno, @soNhaTenDuong, @matKhau, @email, @chuThich);
END
GO

-- Update NhaCungCap
CREATE PROCEDURE usp_NhaCungCap_Update (
    @maNCC CHAR(10),
    @tenNCC NVARCHAR(100),
    @SDT CHAR(11),
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(100),
    @matKhau VARCHAR(100), -- Optional: Only provide if updating. HASH THIS!
    @email VARCHAR(50),
    @chuThich NVARCHAR(100) = NULL
)
AS
BEGIN
    SET NOCOUNT ON;
    -- Add validation/error handling as needed
    UPDATE dbo.NhaCungCap
    SET tenNCC = @tenNCC,
        SDT = @SDT,
        PXno = @PXno,
        soNhaTenDuong = @soNhaTenDuong,
        -- Only update password if a new one is provided and not empty/null
        matKhau = CASE WHEN @matKhau IS NOT NULL AND @matKhau <> '' THEN @matKhau ELSE matKhau END,
        email = @email,
        chuThich = @chuThich
    WHERE maNCC = @maNCC;
END
GO

-- Delete NhaCungCap
CREATE PROCEDURE usp_NhaCungCap_Delete (
    @maNCC CHAR(10)
)
AS
BEGIN
    SET NOCOUNT ON;
    -- Add validation/error handling as needed
    -- Consider implications of ON DELETE CASCADE
    DELETE FROM dbo.NhaCungCap
    WHERE maNCC = @maNCC;
END
GO

-- Get All NhaCungCap
CREATE PROCEDURE usp_NhaCungCap_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maNCC, tenNCC, SDT, PXno, soNhaTenDuong, email, chuThich -- Exclude matKhau
    FROM dbo.NhaCungCap;
END
GO

-- =============================================
-- Stored Procedures for PhieuNhap
-- =============================================
CREATE PROCEDURE usp_PhieuNhap_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maPN, NCCno, ngayNhapHang
    FROM dbo.PhieuNhap;
END
GO

-- =============================================
-- Stored Procedures for DanhMuc
-- =============================================
CREATE PROCEDURE usp_DanhMuc_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maDM, tenDM
    FROM dbo.DanhMuc;
END
GO


-- =============================================
-- Stored Procedures for SanPham
-- =============================================

-- Add SanPham
CREATE PROCEDURE usp_SanPham_Insert (
    @maSP CHAR(7),
    @tenSP NVARCHAR(100),
    @donGiaBan MONEY,
    @soLuongHienCon BIGINT,
    @DMno CHAR(7),
    @linkAnh VARCHAR(100) = NULL
)
AS
BEGIN
    SET NOCOUNT ON;
    IF @donGiaBan < 0 SET @donGiaBan = 0; -- Basic validation
    IF @soLuongHienCon < 0 SET @soLuongHienCon = 0; -- Basic validation

    INSERT INTO dbo.SanPham (maSP, tenSP, donGiaBan, soLuongHienCon, DMno, linkAnh)
    VALUES (@maSP, @tenSP, @donGiaBan, @soLuongHienCon, @DMno, @linkAnh);
END
GO

-- Update SanPham
CREATE PROCEDURE usp_SanPham_Update (
    @maSP CHAR(7),
    @tenSP NVARCHAR(100),
    @donGiaBan MONEY,
    @soLuongHienCon BIGINT, -- Be careful updating this directly, usually done via transactions
    @DMno CHAR(7),
    @linkAnh VARCHAR(100) = NULL
)
AS
BEGIN
    SET NOCOUNT ON;
    IF @donGiaBan < 0 SET @donGiaBan = 0; -- Basic validation
    IF @soLuongHienCon < 0 SET @soLuongHienCon = 0; -- Basic validation

    UPDATE dbo.SanPham
    SET tenSP = @tenSP,
        donGiaBan = @donGiaBan,
        soLuongHienCon = @soLuongHienCon,
        DMno = @DMno,
        linkAnh = @linkAnh
    WHERE maSP = @maSP;
END
GO

-- Delete SanPham
CREATE PROCEDURE usp_SanPham_Delete (
    @maSP CHAR(7)
)
AS
BEGIN
    SET NOCOUNT ON;
    -- Consider implications of ON DELETE CASCADE in ChiTietDonHang, ChiTietPhieuNhap
    DELETE FROM dbo.SanPham
    WHERE maSP = @maSP;
END
GO

-- Get All SanPham
CREATE PROCEDURE usp_SanPham_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maSP, tenSP, donGiaBan, soLuongHienCon, DMno, linkAnh
    FROM dbo.SanPham;
END
GO

-- =============================================
-- Stored Procedures for ChiTietPhieuNhap
-- =============================================
CREATE PROCEDURE usp_ChiTietPhieuNhap_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maPN, maSP, soLuongNhap, giaNhap
    FROM dbo.ChiTietPhieuNhap;
END
GO
-- NOTE: Add/Update/Delete for ChiTietPhieuNhap would ideally update SanPham.soLuongHienCon

-- =============================================
-- Stored Procedures for KhachHang
-- =============================================

-- Add KhachHang
CREATE PROCEDURE usp_KhachHang_Insert (
    @maKH CHAR(10),
    @tenKH NVARCHAR(100),
    @SDT CHAR(11),
    @Email VARCHAR(50),
    @soDuTaiKhoan MONEY,
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(50),
    @matKhau VARCHAR(100), -- HASH THIS IN APPLICATION BEFORE SENDING!
    @gioiTinh NVARCHAR(5) = NULL -- Consider using BIT or TINYINT
)
AS
BEGIN
    SET NOCOUNT ON;
    IF @soDuTaiKhoan < 0 SET @soDuTaiKhoan = 0; -- Basic validation

    INSERT INTO dbo.KhachHang (maKH, tenKH, SDT, Email, soDuTaiKhoan, PXno, soNhaTenDuong, matKhau, gioiTinh)
    VALUES (@maKH, @tenKH, @SDT, @Email, @soDuTaiKhoan, @PXno, @soNhaTenDuong, @matKhau, @gioiTinh);
END
GO

-- Update KhachHang
CREATE PROCEDURE usp_KhachHang_Update (
    @maKH CHAR(10),
    @tenKH NVARCHAR(100),
    @SDT CHAR(11),
    @Email VARCHAR(50),
    @soDuTaiKhoan MONEY,
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(50),
    @matKhau VARCHAR(100), -- Optional: Only provide if updating. HASH THIS!
    @gioiTinh NVARCHAR(5) = NULL
)
AS
BEGIN
    SET NOCOUNT ON;
    IF @soDuTaiKhoan < 0 SET @soDuTaiKhoan = 0; -- Basic validation

    UPDATE dbo.KhachHang
    SET tenKH = @tenKH,
        SDT = @SDT,
        Email = @Email,
        soDuTaiKhoan = @soDuTaiKhoan,
        PXno = @PXno,
        soNhaTenDuong = @soNhaTenDuong,
        -- Only update password if a new one is provided and not empty/null
        matKhau = CASE WHEN @matKhau IS NOT NULL AND @matKhau <> '' THEN @matKhau ELSE matKhau END,
        gioiTinh = @gioiTinh
    WHERE maKH = @maKH;
END
GO

-- Delete KhachHang
CREATE PROCEDURE usp_KhachHang_Delete (
    @maKH CHAR(10)
)
AS
BEGIN
    SET NOCOUNT ON;
    -- Consider implications of ON DELETE CASCADE in DonDatHang_HoaDon
    DELETE FROM dbo.KhachHang
    WHERE maKH = @maKH;
END
GO

-- Get All KhachHang
CREATE PROCEDURE usp_KhachHang_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maKH, tenKH, SDT, Email, soDuTaiKhoan, PXno, soNhaTenDuong, gioiTinh -- Exclude matKhau
    FROM dbo.KhachHang;
END
GO

-- =============================================
-- Stored Procedures for ChucVu
-- =============================================
CREATE PROCEDURE usp_ChucVu_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maCV, tenCV
    FROM dbo.ChucVu;
END
GO

-- =============================================
-- Stored Procedures for NhanVien
-- =============================================

-- Add NhanVien
CREATE PROCEDURE usp_NhanVien_Insert (
    @maNV CHAR(10),
    @tenNV NVARCHAR(100),
    @SDT CHAR(11),
    @Email VARCHAR(50),
    @gioiTinh BIT = 1, -- Default to 1 (needs definition: 1=Male/Female?)
    @DoB DATE,
    @Salary MONEY = 5000000, -- Default Salary
    @ngayVaoLam DATE,
    @PXno CHAR(3),
    @soNhaTenDuong NVARCHAR(50),
    @ChucVuno CHAR(7)
)
AS
BEGIN
    SET NOCOUNT ON;
    -- Add validation: Check DoB for age >= 18, Salary >= 0 etc.
    IF @Salary < 0 SET @Salary = 0;
    IF DATEDIFF(YEAR, @DoB, GETDATE()) < 18
    BEGIN
        -- Raise an error or handle invalid age
        RAISERROR('Employee must be at least 18 years old.', 16, 1);
        RETURN; -- Stop execution
    END

    INSERT INTO dbo.NhanVien (maNV, tenNV, SDT, Email, gioiTinh, DoB, Salary, ngayVaoLam, PXno, soNhaTenDuong, ChucVuno)
    VALUES (@maNV, @tenNV, @SDT, @Email, @gioiTinh, @DoB, @Salary, @ngayVaoLam, @PXno, @soNhaTenDuong, @ChucVuno);
END
GO

-- Update NhanVien
CREATE PROCEDURE usp_NhanVien_Update (
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
)
AS
BEGIN
    SET NOCOUNT ON;
    -- Add validation: Check DoB for age >= 18, Salary >= 0 etc.
    IF @Salary < 0 SET @Salary = 0;
     IF DATEDIFF(YEAR, @DoB, GETDATE()) < 18
    BEGIN
        -- Raise an error or handle invalid age
        RAISERROR('Employee must be at least 18 years old.', 16, 1);
        RETURN; -- Stop execution
    END

    UPDATE dbo.NhanVien
    SET tenNV = @tenNV,
        SDT = @SDT,
        Email = @Email,
        gioiTinh = @gioiTinh,
        DoB = @DoB,
        Salary = @Salary,
        ngayVaoLam = @ngayVaoLam,
        PXno = @PXno,
        soNhaTenDuong = @soNhaTenDuong,
        ChucVuno = @ChucVuno
    WHERE maNV = @maNV;
END
GO

-- Delete NhanVien
CREATE PROCEDURE usp_NhanVien_Delete (
    @maNV CHAR(10)
)
AS
BEGIN
    SET NOCOUNT ON;
    -- Consider implications of ON DELETE CASCADE/SET NULL in DonDatHang_HoaDon
    DELETE FROM dbo.NhanVien
    WHERE maNV = @maNV;
END
GO

-- Get All NhanVien
CREATE PROCEDURE usp_NhanVien_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maNV, tenNV, SDT, Email, gioiTinh, DoB, Salary, ngayVaoLam, PXno, soNhaTenDuong, ChucVuno
    FROM dbo.NhanVien;
END
GO


-- =============================================
-- Stored Procedures for DonDatHang_HoaDon (Hóa Đơn)
-- =============================================

-- Add DonDatHang_HoaDon
CREATE PROCEDURE usp_DonDatHang_HoaDon_Insert (
    @maDH CHAR(10),
    @NVno CHAR(10) = NULL, -- Allow null if order placed online?
    @KHno CHAR(10),
    @ngayTaoDH DATE = NULL -- Use default if not provided
)
AS
BEGIN
    SET NOCOUNT ON;
    IF @ngayTaoDH IS NULL SET @ngayTaoDH = GETDATE();

    INSERT INTO dbo.DonDatHang_HoaDon (maDH, NVno, KHno, ngayTaoDH)
    VALUES (@maDH, @NVno, @KHno, @ngayTaoDH);

    -- Return the generated maDH if needed (especially if auto-generated)
    -- SELECT @maDH AS GeneratedMaDH;
END
GO

-- Update DonDatHang_HoaDon (Limited based on current schema)
CREATE PROCEDURE usp_DonDatHang_HoaDon_Update (
    @maDH CHAR(10),
    @NVno CHAR(10) = NULL,
    @KHno CHAR(10),
    @ngayTaoDH DATE
)
AS
BEGIN
    SET NOCOUNT ON;
    -- Add validation as needed
    UPDATE dbo.DonDatHang_HoaDon
    SET NVno = @NVno,
        KHno = @KHno,
        ngayTaoDH = @ngayTaoDH
    WHERE maDH = @maDH;
END
GO

-- Delete DonDatHang_HoaDon
CREATE PROCEDURE usp_DonDatHang_HoaDon_Delete (
    @maDH CHAR(10)
)
AS
BEGIN
    SET NOCOUNT ON;
    -- IMPORTANT: This will also delete related ChiTietDonHang records
    -- if ON DELETE CASCADE is set on the foreign key in ChiTietDonHang.
    -- Ensure this is the desired behavior.
    DELETE FROM dbo.DonDatHang_HoaDon
    WHERE maDH = @maDH;
END
GO

-- Get All DonDatHang_HoaDon
CREATE PROCEDURE usp_DonDatHang_HoaDon_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maDH, NVno, KHno, ngayTaoDH
    FROM dbo.DonDatHang_HoaDon;
END
GO

-- =============================================
-- Stored Procedures for ChiTietDonHang
-- =============================================
-- IMPORTANT: Assumes you add `donGia MONEY` column back to ChiTietDonHang table
-- IMPORTANT: Does NOT currently handle inventory updates (SanPham.soLuongHienCon)

-- Add ChiTietDonHang
CREATE PROCEDURE usp_ChiTietDonHang_Insert (
    @maDH CHAR(10),
    @maSP CHAR(7),
    @soLuongDat INT,
    @donGia MONEY -- Price at the time of order - ADD THIS COLUMN TO TABLE
    -- @tongTien MONEY -- Removed as it should be calculated
)
AS
BEGIN
    SET NOCOUNT ON;
    -- TODO: Add validation: Check if @maDH exists, @maSP exists
    -- TODO: Check if SanPham.soLuongHienCon >= @soLuongDat
    -- TODO: Wrap in TRANSACTION
    -- TODO: Update SanPham.soLuongHienCon = SanPham.soLuongHienCon - @soLuongDat WHERE maSP = @maSP

    IF @soLuongDat <= 0
    BEGIN
        RAISERROR('Quantity must be positive.', 16, 1);
        RETURN;
    END
    IF @donGia < 0
    BEGIN
        RAISERROR('Unit price cannot be negative.', 16, 1);
        RETURN;
    END

    -- Check if the combination already exists (prevent duplicates)
    IF EXISTS (SELECT 1 FROM dbo.ChiTietDonHang WHERE maDH = @maDH AND maSP = @maSP)
    BEGIN
         RAISERROR('This product already exists in the order. Use Update procedure.', 16, 1);
         RETURN;
    END

    INSERT INTO dbo.ChiTietDonHang (maDH, maSP, soLuongDat, donGia) -- Added donGia
    VALUES (@maDH, @maSP, @soLuongDat, @donGia);

    -- TODO: COMMIT TRANSACTION
END
GO

-- Update ChiTietDonHang
CREATE PROCEDURE usp_ChiTietDonHang_Update (
    @maDH CHAR(10),
    @maSP CHAR(7),
    @soLuongDat INT,
    @donGia MONEY -- Price might change if re-negotiated? Or fixed? Decide business rule.
)
AS
BEGIN
    SET NOCOUNT ON;
    -- TODO: Add validation
    -- TODO: Wrap in TRANSACTION
    -- TODO: Calculate quantity difference (new_qty - old_qty)
    -- TODO: Check if sufficient stock for the difference (if increasing qty)
    -- TODO: Update SanPham.soLuongHienCon based on quantity difference

    IF @soLuongDat <= 0
    BEGIN
        RAISERROR('Quantity must be positive.', 16, 1);
        RETURN;
    END
     IF @donGia < 0
    BEGIN
        RAISERROR('Unit price cannot be negative.', 16, 1);
        RETURN;
    END

    UPDATE dbo.ChiTietDonHang
    SET soLuongDat = @soLuongDat,
        donGia = @donGia -- Update price if needed
    WHERE maDH = @maDH AND maSP = @maSP;

     -- TODO: COMMIT TRANSACTION
END
GO

-- Delete ChiTietDonHang
CREATE PROCEDURE usp_ChiTietDonHang_Delete (
    @maDH CHAR(10),
    @maSP CHAR(7)
)
AS
BEGIN
    SET NOCOUNT ON;
    -- TODO: Wrap in TRANSACTION
    -- TODO: Get the soLuongDat being deleted
    -- TODO: Update SanPham.soLuongHienCon = SanPham.soLuongHienCon + (deleted quantity) WHERE maSP = @maSP

    -- Optional: Get quantity before deleting if needed for inventory adjustment
    -- DECLARE @deletedQty INT;
    -- SELECT @deletedQty = soLuongDat FROM dbo.ChiTietDonHang WHERE maDH = @maDH AND maSP = @maSP;

    DELETE FROM dbo.ChiTietDonHang
    WHERE maDH = @maDH AND maSP = @maSP;

    -- TODO: Update inventory using @deletedQty
    -- TODO: COMMIT TRANSACTION
END
GO

-- Get All ChiTietDonHang
CREATE PROCEDURE usp_ChiTietDonHang_GetAll
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maDH, maSP, soLuongDat, donGia -- Added donGia
    FROM dbo.ChiTietDonHang;
END
GO

-- Get ChiTietDonHang By MaDH (Get details for a specific order)
CREATE PROCEDURE usp_ChiTietDonHang_GetByMaDH (
    @maDH CHAR(10)
)
AS
BEGIN
    SET NOCOUNT ON;
    SELECT maDH, maSP, soLuongDat, donGia -- Added donGia
    FROM dbo.ChiTietDonHang
    WHERE maDH = @maDH;
END
GO

