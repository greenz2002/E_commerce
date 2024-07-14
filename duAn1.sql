create database DuAn_1_Nhom1
go
use DuAn_1_Nhom1
go

create table danhMuc(
 idDM uniqueidentifier primary key default newId(),
 maDM varchar(10),
 tenDM nvarchar(50)
)

create table thuongHieu(
 idTH uniqueidentifier primary key default newId(),
 maTH varchar(10),
 tenTH nvarchar(20)
)
create table quanAo(
 idQA uniqueidentifier primary key default newId(),
 id_thuongHieu uniqueidentifier,
 id_danhMuc uniqueidentifier,
 maQA varchar(10),
 tenQA nvarchar(200)
)

create table mauSac(
 idMS uniqueidentifier primary key default newId(),
 maMS varchar(10),
 tenMS nvarchar(20)
)

create table size(
 idS uniqueidentifier primary key default newId(),
 maS varchar(10),
 tenS nvarchar(20)
)

create table chatLieu(
 idCL uniqueidentifier primary key default newId(),
 maCL varchar(10),
 tenCL nvarchar(50)
)

create table chiTietSP(
 idCTSP uniqueidentifier primary key default newId(),
 id_quanAo uniqueidentifier,
 id_mauSac uniqueidentifier,
 id_size uniqueidentifier,
 id_chatLieu uniqueidentifier,
 soLuong int,
 moTa nvarchar(max),
 namSX int,
 giaBan decimal,
 trangThai nvarchar(20)
)

create table doiTra(
 idDT uniqueidentifier primary key default newId(),
 id_HoaDon uniqueidentifier,
 ngayDoiTra datetime,
 liDoDOiTra nvarchar(max),
 trangThai nvarchar(20)
)

create table chiTietHoaDOn(
 idCTHD uniqueidentifier primary key default newId(),
 id_old uniqueidentifier,
 id_hoaDon uniqueidentifier,
 id_chiTietSP uniqueidentifier,
 soLuong int,
 donGia decimal,
 trangThai nvarchar(20)
)

create table phuongThucThanhToan(
 idPTTT uniqueidentifier primary key default newId(),
 maPTTT varchar(10),
 tenPTTT nvarchar(20),
 trangThai nvarchar(20)
)

create table hoaDOn(
 idHD uniqueidentifier primary key default newId(),
 id_nhanVien uniqueidentifier,
 id_hoaKhachHang uniqueidentifier,
 id_DoiTra uniqueidentifier,
 id_PhuongThucThanhToan uniqueidentifier,
 ngayTao datetime,
 ngayThanhToan datetime,
 ngayShip datetime,
 ngayNhan date,
 giaTien decimal,
 trangThai nvarchar(20)
)

create table khuyenMai(
 idKM uniqueidentifier primary key default newId(),
 maKM varchar(10),
 tenKM nvarchar(100),
 ngayBatDau date,
 ngayKetThuc date,
 GiaGiam nvarchar(20),
 trangThai nvarchar(20)
)
create table hoaDonKhuyenMai(
 idHDKM uniqueidentifier primary key default newId(),
 id_hoaDon uniqueidentifier,
 id_khuyenMai uniqueidentifier,
 TongTienSauKhiGiam decimal,
)
create table chucVu(
 idCV uniqueidentifier primary key default newId(),
 maCV varchar(10),
 tenCV nvarchar(30)
)

create table nhanVien(
 idNV uniqueidentifier primary key default newId(),
 id_chucVu uniqueidentifier,
 maNV varchar(10),
 tenNV nvarchar(50),
 ngaySinh date,
 diaChi nvarchar(100),
 gioiTinh int,
 soDienThoai nvarchar(10),
 matKhau varchar(50),
 trangThai nvarchar(20)
)

create table khachHang(
 idKH uniqueidentifier primary key default newId(),
 ma varchar(10),
 ten nvarchar(20),
 soDienThoai nvarchar(10),
 diaChi nvarchar(50),
 ngaySinh date
)

--FK cua quanAO
alter table quanAo add constraint quanAo_danhMuc_FK foreign key (id_danhMuc) references danhMuc(idDM)
alter table quanAo add constraint quanAo_thuongHieu_FK foreign key (id_thuongHieu) references thuongHieu(idTH)

--
--FK cua chi tiet san pham
alter table chiTietSP add constraint chiTietSP_quanAo_FK foreign key (id_quanAo) references quanAo(idQA)
alter table chiTietSP add constraint chiTietSP_mauSac_FK foreign key (id_mauSac) references mauSac(idMS)
alter table chiTietSP add constraint chiTietSP_size_FK foreign key (id_size) references size(idS)
alter table chiTietSP add constraint chiTietSP_chatLieu_FK foreign key (id_chatLieu) references chatLieu(idCL)

--FK cua chi tiet hoa don
alter table chiTietHoaDOn add constraint chiTietHoaDOn_hoaDOn_FK foreign key (id_hoaDon) references hoaDOn(idHD)
alter table chiTietHoaDOn add constraint chiTietHoaDOn_chiTietHoaDOn_FK foreign key (id_old) references chiTietHoaDOn(idCTHD)
alter table chiTietHoaDOn add constraint chiTietHoaDOn_chiTietSP_FK foreign key (id_chiTietSP) references chiTietSP(idCTSP)

--FK cua hoa don
alter table hoaDOn add constraint hoaDOn_nhanVien_FK foreign key (id_nhanVien) references nhanVien(idNV)
alter table hoaDOn add constraint hoaDOn_khachHang_FK foreign key (id_hoaKhachHang) references khachHang(idKH)
alter table hoaDOn add constraint hoaDOn_doiTra_FK foreign key (id_DoiTra) references doiTra(idDT)
alter table hoaDOn add constraint hoaDOn_phuongThucThanhToan_FK foreign key (id_PhuongThucThanhToan) references phuongThucThanhToan(idPTTT)

--FK cua hoa don khuyen mai
alter table hoaDonKhuyenMai add constraint hoaDonKhuyenMai_hoaDOn_FK foreign key (id_hoaDon) references hoaDOn(idHD)
alter table hoaDonKhuyenMai add constraint hoaDonKhuyenMai_khuyenMai_FK foreign key (id_khuyenMai) references khuyenMai(idKM)

--FK cua nhan vien
alter table nhanVien add constraint nhanVien_chucVu_FK foreign key (id_chucVu) references chucVu(idCV)

--FK cua khuyen mai
alter table khuyenMai add ghiChu nvarchar(200)

--FK cua khuyen mai
alter table hoaDOn add maHD nvarchar(10)

--chuc vu
insert into chucVu(maCV,tenCV) values(N'CV01',N'Quản lý')
insert into chucVu(maCV,tenCV) values(N'CV02',N'Nhân viên')

--Phương thức thanh toán insert 28/11/2023
insert into phuongThucThanhToan(maPTTT,tenPTTT,trangThai) values(N'TT01',N'Chuyển khoản',N'Hoạt động')
insert into phuongThucThanhToan(maPTTT,tenPTTT,trangThai) values(N'TT02',N'Trả trực tiếp',N'Hoạt động')

--Chất liệu insert
INSERT INTO chatLieu (maCL, tenCL)
VALUES 
('CL001', N'Bông'),
('CL002', N'Nhung'),
('CL003', N'Da lộn'),
('CL004', N'Lụa'),
('CL005', N'Len')

--Size insert
INSERT INTO size (maS, tenS)
VALUES 
('S001', 'S'),
('S002', 'M'),
('S003', 'L'),
('S004', 'XL'),
('S005', 'XXL')

--Màu sắc insert
INSERT INTO mauSac (maMS, tenMS)
VALUES 
('MS001', N'Đỏ'),
('MS002', N'Vàng'),
('MS003', N'Xám'),
('MS004', N'Đen'),
('MS005', N'Trắng')

--Danh mục insert
INSERT INTO danhMuc (maDM, tenDM)
VALUES 
('DM001', N'Áo sơ mi nam'),
('DM002', N'Áo khoác da nam'),
('DM003', N'Áo thun nữ'),
('DM004', N'Quần jean nam'),
('DM005', N'Váy dài nữ')

--Thương hiệu insert
INSERT INTO thuongHieu (maTH, tenTH)
VALUES 
('TH001', N'Louis Vuitton'),
('TH002', N'Gucci'),
('TH003', N'Hermes'),
('TH004', N'Prada'),
('TH005', N'Chanel')

--Khách hàng insert
INSERT INTO khachHang (ma, ten, soDienThoai, diaChi, ngaySinh)
VALUES 
('KH001', N'Nguyến Tuân', '0987654321', N'Địa chỉ A', '1990-01-01'),
('KH002', N'Phạm Đinh Tống', '0987654322', N'Địa chỉ B', '1985-05-15'),
('KH003', N'Nguyễn Thu Trang', '0987654323', N'Địa chỉ C', '1995-10-20'),
('KH004', N'Phạm Thị Lệ', '0987654324', N'Địa chỉ D', '2000-03-30'),
('KH005', N'Lê Ngọc Bích', '0987654325', N'Địa chỉ E', '1988-12-10')

--Nhân viên insert
--select * from chucVu
--INSERT INTO nhanVien (id_chucVu, maNV, tenNV, ngaySinh, diaChi, gioiTinh, soDienThoai, matKhau, trangThai)
--VALUES 
--('', N'NV001', N'Nguyễn Văn An', '1990-01-15', N'Địa chỉ 1', 1, '0987654326', 'password1', N'Hoạt động'),
--('', N'NV002', N'Nguyễn Thị Hân', '1995-03-20', N'Địa chỉ 2', 0, '0987654327', 'password2', N'Hoạt động'),
--('', N'NV003', N'Trần Văn Chính', '1988-05-10', N'Địa chỉ 3', 1, '0987654328', 'password3', N'Hoạt động'),
--('', N'NV004', N'Lê Thị Dung', '1992-07-25', N'Địa chỉ 4', 0, '0987654329', 'password4', N'Hoạt động'),
--('', N'NV005', N'Phạm Văn Hiếu', '1986-11-05', N'Địa chỉ 5', 1, '0987654310', 'password5', N'Nghỉ việc')

--Sản phẩm insert
--select*from thuongHieu
--select*from danhMuc
--INSERT INTO quanAo (id_thuongHieu, id_danhMuc, maQA, tenQA)
--VALUES 
--('75A77F71-88AF-46CF-94C3-275D69856218', '4E8408A8-196E-4B78-9859-3EAB1F917CC4', N'QA001', N'Váy 2 dây có nút điều chỉnh ngắn dài ôm body chất tăm lạnh co dãn '),
--('F80DC901-49E4-4889-858B-2F2CB88E5A51', '4E28EC5F-5C0D-46AB-A4C7-D892CA788E3F', N'QA002', N'Áo Sơ Mi Dài Tay Form Rộng Phong Cách retro Nhật')

alter table chiTietSP add maCT nvarchar(10)

--Chi tiết hóa đơn alter 02/12/2023
alter table chiTietHoaDOn add maCTHD nvarchar(10)