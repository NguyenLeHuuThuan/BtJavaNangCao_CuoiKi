--Xóa dtb nếu như đã tồn tại
IF EXISTS (SELECT * FROM sys.databases WHERE name = N'QLBH_2115')
BEGIN
    -- Đóng tất cả các kết nối đến cơ sở dữ liệu
    EXECUTE sp_MSforeachdb 'IF ''?'' = ''QLBH_2115'' 
    BEGIN 
        DECLARE @sql AS NVARCHAR(MAX) = ''USE [?]; ALTER DATABASE [?] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;''
        EXEC (@sql)
    END'
    -- Xóa tất cả các kết nối tới cơ sở dữ liệu (thực hiện qua hệ thống master)
    USE master;

    -- Xóa cơ sở dữ liệu nếu tồn tại
    DROP DATABASE QLBH_2115;
END
-- Tạo database
CREATE DATABASE QLBH_2115;
GO
USE QLBH_2115;
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
    SDT CHAR(11) UNIQUE CHECK (SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' 
                             OR SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    nhanVienLienHe NVARCHAR(100),
    PXno CHAR(3) FOREIGN KEY REFERENCES PhuongXa(maPX) ON DELETE CASCADE ON UPDATE CASCADE,
    soNhaTenDuong NVARCHAR(100),
	matKhau VARCHAR(100)
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
    soLuongCanDoi SMALLINT CHECK (soLuongCanDoi <= 5) DEFAULT 5,
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
    SDT CHAR(11) UNIQUE CHECK (SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' 
                              OR SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    Email VARCHAR(50) UNIQUE CHECK (Email LIKE '[a-z]%@%'),
    soDuTaiKhoan MONEY,
    PXno CHAR(3) FOREIGN KEY REFERENCES PhuongXa(maPX) ON DELETE CASCADE ON UPDATE CASCADE,
    soNhaTenDuong NVARCHAR(50),
	matKhau VARCHAR(100)
);

-- Tạo bảng Nhân Viên
CREATE TABLE NhanVien (
    maNV CHAR(10) PRIMARY KEY,
    tenNV NVARCHAR(100),
    SDT CHAR(11) UNIQUE CHECK (SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' 
                              OR SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    Email VARCHAR(50) UNIQUE CHECK (Email LIKE '[a-z]%@%'),
    gioiTinh BIT DEFAULT 1 CHECK (gioiTinh IN (0, 1)),
    DoB DATE CHECK (DATEDIFF(YEAR, DoB, GETDATE()) >= 18),
    Salary MONEY CHECK (Salary >= 0) DEFAULT 5000000
);

-- Tạo bảng Đơn Đặt Hàng - Hóa Đơn
CREATE TABLE DonDatHang_HoaDon (
    maDH CHAR(10) PRIMARY KEY,
    NVno CHAR(10) FOREIGN KEY REFERENCES NhanVien(maNV) ON DELETE CASCADE ON UPDATE CASCADE,
    KHno CHAR(10) FOREIGN KEY REFERENCES KhachHang(maKH) ON DELETE CASCADE ON UPDATE CASCADE,
    ngayTaoDH DATE DEFAULT GETDATE(),
    SDTGiaoHang VARCHAR(11) UNIQUE CHECK (SDTGiaoHang LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' 
                                     OR SDTGiaoHang LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    maHoaDonDienTu CHAR(10),
    ngayThanhToan DATE,
    ngayGiaoHang DATE,
    trangThaiDonHang NVARCHAR(30) DEFAULT N'Đã đặt hàng' 
        CHECK (trangThaiDonHang IN (N'Thành công', N'Thất bại', N'Chờ xử lý', N'Đang xử lý', N'Đã đặt hàng')),
    PXno CHAR(3) FOREIGN KEY REFERENCES PhuongXa(maPX) ON DELETE NO ACTION ON UPDATE NO ACTION,
    soNhaTenDuong NVARCHAR(50),
    CONSTRAINT CK_DDHHD_ngayThanhToan CHECK (ngayThanhToan >= ngayTaoDH),
    CONSTRAINT CK_DDHHD_ngayGiaoHang CHECK (ngayGiaoHang >= ngayThanhToan)
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

-- Quốc Gia
INSERT INTO QuocGia VALUES
    ('QG01', N'Việt Nam'), ('QG02', N'Lào'), ('QG03', N'Campuchia'), ('QG04', N'Pháp'),
    ('QG05', N'Bồ Đào Nha'), ('QG06', N'Brazil'), ('QG07', N'Cu Ba'), ('QG08', N'Nga'),
    ('QG09', N'Anh'), ('QG10', N'Nhật');

-- Tỉnh Thành
INSERT INTO TinhThanh VALUES
    ('T01', 'QG01', N'Quảng Nam'), ('T02', 'QG01', N'Đà Nẵng'), ('T03', 'QG01', N'Quảng Ninh'),
    ('T04', 'QG01', N'Gia Lai'), ('T05', 'QG01', N'Quảng Trị'), ('T06', 'QG01', N'Quảng Ngãi'),
    ('T07', 'QG01', N'Hồ Chí Minh'), ('T08', 'QG01', N'Kon Tum'), ('T09', 'QG01', N'Huế'),
    ('T10', 'QG01', N'Đà Lạt');

-- Quận Huyện
INSERT INTO QuanHuyen VALUES
    ('H01', 'T01', N'Điện Bàn'), ('H02', 'T01', N'Đại Lộc'), ('H03', 'T01', N'Tam Kỳ'),
    ('H04', 'T01', N'Núi Thành'), ('H05', 'T01', N'Thăng Bình'), ('H06', 'T01', N'Quế Sơn'),
    ('H07', 'T01', N'Hồ Chí Minh'), ('H08', 'T01', N'Hội An'), ('H09', 'T01', N'Phú Ninh'),
    ('H10', 'T01', N'Duy Xuyên');

-- Phường Xã
INSERT INTO PhuongXa VALUES
    ('P01', 'H01', N'Điện Tiến'), ('P02', 'H01', N'Điện Thọ'), ('P03', 'H01', N'Điện Hồng'),
    ('P04', 'H01', N'Điện Phước'), ('P05', 'H01', N'Điện Minh'), ('P06', 'H01', N'Vĩnh Điện'),
    ('P07', 'H01', N'Điện Hòa'), ('P08', 'H01', N'Điện Phong'), ('P09', 'H01', N'Điện Quang'),
    ('P10', 'H01', N'Điện An');

-- Nhà Cung Cấp
INSERT INTO NhaCungCap (maNCC, tenNCC, SDT, nhanVienLienHe, soNhaTenDuong, PXno) VALUES
    ('NCC01', 'Nike', '0896998961', N'Đặng Công Kiệt', N'123/35 Cù Chính Lan', 'P01'),
    ('NCC02', 'Adidas', '0123456789', N'Nguyễn Thị Khánh Ly', N'12 Cao Thắng', 'P03'),
    ('NCC03', 'Mizuno', '0463728194', N'Nguyễn Nữ Khánh Ngọc', N'13 Cao Thắng', 'P05'),
    ('NCC04', 'Puma', '0849384394', N'Huỳnh Ngọc Quyền', N'23 Lý Thường Kiệt', 'P06'),
    ('NCC05', 'Kelme', '0480394058', N'Hồ Minh Quân', N'100 Ông Ích Khiêm', 'P01'),
    ('NCC06', 'Akka', '0485930194', N'Nguyễn Lê Hữu Thuận', N'12 Nguyễn Văn Trỗi', 'P05'),
    ('NCC07', 'Jogaborla', '0384909090', N'Trần Thị Minh Ánh', N'20 Hoàng Hoa Thám', 'P01'),
    ('NCC08', 'Kamito', '0909090909', N'Đặng Hồng Ân', N'25 Khuê Trung', 'P01'),
    ('NCC09', 'Zocker', '0101010101', N'Đặng Công Khôi', N'1 Hai Bà Trưng', 'P04'),
    ('NCC10', 'Wika', '0202020202', N'Trương Thị Kiều Nhi', N'23 Điện Biên Phủ', 'P01');

-- Khách Hàng
INSERT INTO KhachHang (maKH, tenKH, SDT, Email, soDuTaiKhoan, soNhaTenDuong, PXno) VALUES
    ('KH01', N'Nguyễn Văn A', '0776234589', 'vana@gmail.com', 1000000, N'1 Cao Thắng', 'P01'),
    ('KH02', N'Đặng Công B', '0707070707', 'congb@gmail.com', 1000000, N'2 Cao Thắng', 'P02'),
    ('KH03', N'Nguyễn Thị C', '0101020303', 'thic@gmail.com', 1000000, N'15 Huỳnh Ngọc Huệ', 'P05'),
    ('KH04', N'Đinh Văn D', '0123123123', 'vand@gmail.com', 1000000, N'20 Huỳnh Ngọc Huệ', 'P01'),
    ('KH05', N'Võ Văn T', '0303030303', 'vant@gmail.com', 1000000, N'12 Võ Nguyên Giáp', 'P05'),
    ('KH06', N'Ngũ Văn K', '0120120120', 'vank@gmail.com', 1000000, N'35 Lý Thái Tổ', 'P07'),
    ('KH07', N'Lý Lê G', '0404040404', 'leg@gmail.com', 1000000, N'12 Nguyễn Tất Thành', 'P10'),
    ('KH08', N'Trần Thu Sang', '0123456709', 'thusang@gmail.com', 1000000, N'13 Điện Biên Phủ', 'P09'),
    ('KH09', N'Nguyễn Thị Thu V', '0808080808', 'thuv@gmail.com', 1000000, N'100 Võ Nguyên Giáp', 'P08'),
    ('KH10', N'Đoàn Quang T', '0909080909', 'quangt@gmail.com', 1000000, N'3 Lê Đại Hành', 'P05');

-- Nhân Viên
INSERT INTO NhanVien (maNV, tenNV, SDT, Email, gioiTinh, DoB, Salary) VALUES
    ('NV01', N'Trần Thị Minh Ánh', '0934803059', 'minhanh1981@gmail.com', DEFAULT, '20-10-1981', DEFAULT),
    ('NV02', N'Đặng Công Minh', '0569487477', 'abc@gmail.com', DEFAULT, '19-10-1981', DEFAULT),
    ('NV03', N'Nguyễn Thuận', '0274072308', 'ahsas@gmail.com', DEFAULT, '22-10-1981', DEFAULT),
    ('NV04', N'ABC', '0405273659', 'aod@gmail.com', DEFAULT, '21-10-1981', DEFAULT),
    ('NV05', N'DGBC', '0568316916', 'asidjsa@gmail.com', DEFAULT, '23-10-1981', DEFAULT),
    ('NV06', N'DHKD DKDH DKHHD', '0755334629', 'asdasd@gmail.com', DEFAULT, '25-10-1981', DEFAULT),
    ('NV07', N'JDJD JDJD', '0627062475', 'asdsadas@gmail.com', DEFAULT, '20-10-1981', DEFAULT),
    ('NV08', N'KO KO', '0556392705', 'asdsadad@gmail.com', DEFAULT, '30-10-1981', DEFAULT),
    ('NV09', N'OIOI IOII', '0541998627', 'weqeqw@gmail.com', DEFAULT, '10-10-1981', DEFAULT),
    ('NV10', N'KOKOK KKK', '0276762649', 'sdfsdfs@gmail.com', DEFAULT, '20-10-1981', DEFAULT);

-- Sản Phẩm
INSERT INTO SanPham (maSP, tenSP, donGiaBan, soLuongHienCon) VALUES
    ('SP01', 'mercurial vapor 15 pro', 2000000, 100),
    ('SP02', 'mercurial vapor 15 academy', 2000000, 100),
    ('SP03', 'mercurial vapor 14 pro', 2000000, 100),
    ('SP04', 'mercurial vapor 14 academy', 2000000, 100),
    ('SP05', 'mercurial vapor 13 pro', 2000000, 100),
    ('SP06', 'mercurial vapor 13 academy', 2000000, 100),
    ('SP07', 'mercurial vapor 16 pro', 2000000, 100),
    ('SP08', 'mercurial vapor 16 academy', 2000000, 100),
    ('SP09', 'mercurial superfly 9', 2000000, 100),
    ('SP10', 'mercurial superfly 10', 2000000, 100);

-- Phiếu Nhập
INSERT INTO PhieuNhap (maPN, NCCno, ngayNhapHang) VALUES
    ('PN01', 'NCC01', '20-10-2024'), ('PN02', 'NCC01', '12-10-2024'), ('PN03', 'NCC01', '01-04-2024'),
    ('PN04', 'NCC01', '03-05-2024'), ('PN05', 'NCC01', '20-07-2024'), ('PN06', 'NCC01', '25-08-2024'),
    ('PN07', 'NCC01', '28-10-2024'), ('PN08', 'NCC01', '29-01-2024'), ('PN09', 'NCC01', '30-12-2024'),
    ('PN10', 'NCC01', '27-01-2024');

-- Chi Tiết Phiếu Nhập
INSERT INTO ChiTietPhieuNhap (maPN, maSP, soLuongNhap, giaNhap) VALUES
    ('PN01', 'SP01', 100, 1000000), ('PN01', 'SP02', 100, 1000000), ('PN01', 'SP03', 100, 1000000),
    ('PN01', 'SP04', 100, 1000000), ('PN02', 'SP01', 100, 1000000), ('PN03', 'SP01', 100, 1000000),
    ('PN04', 'SP01', 100, 1000000), ('PN05', 'SP01', 100, 1000000), ('PN06', 'SP01', 100, 1000000),
    ('PN06', 'SP02', 100, 1000000);

-- Đơn Đặt Hàng - Hóa Đơn
INSERT INTO DonDatHang_HoaDon (maDH, NVno, KHno, SDTGiaoHang, maHoaDonDienTu, ngayThanhToan, ngayGiaoHang, trangThaiDonHang, soNhaTenDuong, PXno) VALUES
    ('DH01', 'NV01', 'KH01', '0909090909', 'HD01', GETDATE()+1, GETDATE()+2, N'Đã đặt hàng', N'1 Hai Bà Trưng', 'P01'),
    ('DH02', 'NV01', 'KH02', '0808080808', 'HD02', GETDATE()+1, GETDATE()+2, N'Đã đặt hàng', N'1 Hai Bà Trưng', 'P01'),
    ('DH03', 'NV01', 'KH03', '0909090907', 'HD03', GETDATE()+1, GETDATE()+2, N'Chờ xử lý', N'1 Hai Bà Trưng', 'P01'),
    ('DH04', 'NV02', 'KH01', '0909090906', 'HD04', GETDATE()+1, GETDATE()+2, N'Đang xử lý', N'1 Hai Bà Trưng', 'P01'),
    ('DH05', 'NV02', 'KH02', '0909090905', 'HD05', GETDATE()+1, GETDATE()+2, N'Thất bại', N'1 Hai Bà Trưng', 'P01'),
    ('DH06', 'NV02', 'KH03', '0909090904', 'HD06', GETDATE()+1, GETDATE()+2, N'Thành công', N'1 Hai Bà Trưng', 'P01'),
    ('DH07', 'NV03', 'KH01', '0909090903', 'HD07', GETDATE()+1, GETDATE()+2, N'Thành công', N'1 Hai Bà Trưng', 'P01'),
    ('DH08', 'NV03', 'KH02', '0909090902', 'HD08', GETDATE()+1, GETDATE()+2, N'Thành công', N'1 Hai Bà Trưng', 'P01'),
    ('DH09', 'NV03', 'KH03', '0909090901', 'HD09', GETDATE()+1, GETDATE()+2, N'Thành công', N'1 Hai Bà Trưng', 'P01'),
    ('DH10', 'NV03', 'KH04', '0909090900', 'HD10', GETDATE()+1, GETDATE()+2, N'Thành công', N'1 Hai Bà Trưng', 'P01');

-- Chi Tiết Đơn Hàng
INSERT INTO ChiTietDonHang (maDH, maSP, soLuongDat, donGia) VALUES
    ('DH01', 'SP01', 10, 1500000), ('DH01', 'SP02', 10, 1500000), ('DH01', 'SP03', 10, 1500000),
    ('DH02', 'SP01', 10, 1500000), ('DH03', 'SP01', 10, 1500000), ('DH04', 'SP01', 10, 1500000),
    ('DH05', 'SP01', 10, 1500000), ('DH06', 'SP02', 10, 1500000), ('DH07', 'SP03', 10, 1500000),
    ('DH08', 'SP03', 10, 1500000), ('DH09', 'SP01', 10, 1500000), ('DH10', 'SP02', 10, 1500000);
