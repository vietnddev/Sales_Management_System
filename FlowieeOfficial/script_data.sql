INSERT INTO SYS_CONFIG(CODE, NAME, VALUE, SORT) VALUES('EMAIL_HOST', 'Email host', 'smtp', 0);
INSERT INTO SYS_CONFIG(CODE, NAME, VALUE, SORT) VALUES('EMAIL_PORT', 'Email port', '587', 0);
INSERT INTO SYS_CONFIG(CODE, NAME, VALUE, SORT) VALUES('EMAIL_USERNAME', 'Email username', null, 0);
INSERT INTO SYS_CONFIG(CODE, NAME, VALUE, SORT) VALUES('EMAIL_PASSWORD', 'Email password', null, 0);
INSERT INTO SYS_CONFIG(CODE, NAME, VALUE, SORT) VALUES('TIMEOUT', 'Thời gian timeout', null, 0);
INSERT INTO SYS_CONFIG(CODE, NAME, VALUE, SORT) VALUES('PATH_UPLOAD', 'Thư mục lưu file upload', null, 0);
INSERT INTO SYS_CONFIG(CODE, NAME, VALUE, SORT) VALUES('SEND_MAIL_REPORT_DAILY', 'Gửi mail báo cáo hoạt động kinh doanh hàng ngày', null, 0);
INSERT INTO SYS_CONFIG(CODE, NAME, VALUE, SORT) VALUES('MAX_SIZE_UPLOAD', 'Dung lượng file tối đa cho phép upload', null, 0);
INSERT INTO SYS_CONFIG(CODE, NAME, VALUE, SORT) VALUES('EXTENSION_ALLOWED_UPLOAD', 'Định dạng file được phép upload', null, 0);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('UNIT', 'ROOT', 'Đơn vị tính', 0, 0, 'system/category/unit');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('UNIT', null, 'Cái', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('UNIT', null, 'Cuộn', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('UNIT', null, 'Mét', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('UNIT', null, 'Cộng', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PAYMETHOD', 'ROOT', 'Hình thức thanh toán', 0, 0, 'system/category/pay-method');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PAYMETHOD', null, 'Chuyển khoản', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PAYMETHOD', null, 'Tiền mặt', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PAYMETHOD', null, 'Ghi sổ', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('FABRICTYPE', 'ROOT', 'Chất liệu vải', 0, 0, 'system/category/fabric-type');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('FABRICTYPE', null, 'Kaki', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('FABRICTYPE', null, 'Jean', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('FABRICTYPE', null, 'Kate', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('FABRICTYPE', null, 'Voan', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('FABRICTYPE', null, 'Cotton', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SALESCHANNEL', 'ROOT', 'Kênh bán hàng', 0, 0, 'system/category/sales-channel');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SALESCHANNEL', null, 'Shopee', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SALESCHANNEL', null, 'Instagram', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SALESCHANNEL', null, 'Facebook', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SALESCHANNEL', null, 'Zalo', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SIZE', 'ROOT', 'Kích cỡ', 0, 0, 'system/category/size');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SIZE', null, 'S', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SIZE', null, 'M', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SIZE', null, 'L', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('SIZE', null, 'XL', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('COLOR', 'ROOT', 'Màu sắc', 0, 0, 'system/category/color');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('COLOR', null, 'Trắng', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('COLOR', null, 'Đen', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('COLOR', null, 'Cam', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('COLOR', null, 'Vàng', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PRODUCTTYPE', 'ROOT', 'Loại sản phẩm', 0, 0, 'system/category/product-type');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PRODUCTTYPE', null, 'Áo thun', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PRODUCTTYPE', null, 'Áo sơ mi', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PRODUCTTYPE', null, 'Quần tây', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PRODUCTTYPE', null, 'Đầm', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PRODUCTTYPE', null, 'Váy', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('DOCUMENTTYPE', 'ROOT', 'Loại tài liệu', 0, 0, 'system/category/document-type');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('DOCUMENTTYPE', null, 'Văn bản hành chính', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('ORDERSTATUS', 'ROOT', 'Trạng thái đơn hàng', 0, 0, 'system/category/order-status');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('ORDERSTATUS', null, 'Đang chuẩn bị đơn hàng', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('ORDERSTATUS', null, 'Đang chờ đơn vị vận chuyển tiếp nhận', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('ORDERSTATUS', null, 'Đang vận chuyển', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('ORDERSTATUS', null, 'Đã hoàn thành', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PAYMENTSTATUS', 'ROOT', 'Trạng thái thanh toán', 0, 0, 'system/category/payment-status');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PAYMENTSTATUS', 'UNPAID', 'Chưa thanh toán', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PAYMENTSTATUS', 'PARTLY-PAID', 'Thanh toán một phần', 0, 0, null);
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('PAYMENTSTATUS', 'PAID', 'Đã thanh toán', 0, 0, null);

INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('BRAND', 'ROOT', 'Thương hiệu', 0, 0, 'system/category/brand');
INSERT INTO CATEGORY(TYPE, CODE, NAME, STATUS, IS_DEFAULT, ENDPOINT) VALUES('BRAND', null, 'Flowiee', 0, 0, null);