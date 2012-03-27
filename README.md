Giới thiệu
==========
##Dự án vie-hts

Dự án __vie-hts__ (_Vietnamese Human-based Text-to-Speech_) hay _vie-hts@a11y-vietnamese_ là một dự án con nằm trong tổ hợp dự án [a11y-vietnamese](https://github.com/myguidingstar/a11y-vietnamese).
Mục tiêu của vie-hts là có được một __chương trình đọc văn bản tiếng Việt__ (Vietnamese Text-to-Speech) với chất lượng giọng nói đầu ra đủ tốt; đồng thời cung cấp chỉ dẫn (và hỗ trợ) cho cộng đồng có thể tự xây dựng các giọng đọc bổ sung).
Ngoài ra, phần ghi âm của dự án cũng là nguồn dữ liệu đầu vào tương đối đầy đủ để phân tích trên chương trình Praat cho những ai muốn tiếp cận theo theo cách espeak đang làm (xem dưới đây).

## So sánh với Espeak
Giải pháp tương đương sẵn có hiện nay là [Espeak](http://espeak.sourceforge.net), chương trình nhỏ gọn đang được __cài sẵn trong nhiều distro__. Espeak cũng được Google sử dụng để phát âm tiếng Việt.

Để máy tính đọc được tiếng Việt, có một số hướng giải pháp:

* Ghi âm từng từ có nghĩa một (kiểu __wyabdc-real-people-tts__)
	* Ưu: chất lượng đầu ra kiểm soát được 100%
	* Nhược:
		* không/khó mở rộng số từ: khi phát sinh từ mới (đặc điểm cố hữu của ngôn ngữ) phải ghi âm thêm; người dùng phải cập nhật phiên bản)
		* số lượng từ khổng lồ
* Tổng hợp từ các đơn vị âm cơ bản (cách của espeak), dùng thuật toán để sinh ra âm
	* Ưu:
		* đọc được tất cả các từ mà không cần cho sẵn
		* có thể điều chỉnh giọng đọc lên giọng, xuống giọng v.v... bằng tham truyền đến thuật toán.
	* Nhược: vì quá trình phân tích xử lí phức tạp nên cộng đồng khó tham gia đóng góp trực tiếp, chỉ có cá nhân/nhóm nghiên cứu sâu mới có những điều chỉnh đáng kể. Chưa chắc chắn về khả năng cải thiện chất lượng đầu ra.
* Cách tiếp cận của vie-hts:
	* Ghi âm các **phần phụ âm đầu** và **vần** rồi tổng hợp (đơn giản là nối, hoặc hiệu ứng fade?). Các khái niệm vui lòng xem [ở đây](Teminologies.md). Số âm cần ghi dự kiến là:
		* số phần phụ âm đầu: 27
		* số vần: 818
	* Kết hợp cân bằng (nhưng không thể tối đa) ưu, nhược của hai phương pháp trên:
		* tổng số âm phải ghi không quá lớn
		* phát âm được tất cả các từ mà không phải ghi âm thêm.
	* Tạo một voice tên là vie cho espeak (phải viết lại *vie_rules*, *vie_list* và *ph_vie*)
	* Cộng đồng có thể ghi âm để tạo ra giọng đọc mới
		* thậm chí trong mỗi giọng đọc đó có thể tạo (một số) **giọng đọc phụ** được đọc với cung bậc cảm xúc đặc biệt, kèm với giọng đọc thường
		* lưu ý:
			* mỗi giọng đọc là một người đọc (hoặc những người có giọng giống nhau)
			* mỗi giọng đọc phụ này cũng có khối lượng bằng một giọng đoc thường
			* cung bậc cảm xúc đặc biệt có thể tạo ra tự động bằng cách áp dụng các hiệu ứng âm thanh vào giọng đọc chuẩn, tuy nhiên chưa thể chắc chắn về tính tự nhiên của đầu ra.
	* Chi tiết vui lòng xem trong [kế hoạch triển khai]().

## Xem thêm:

[Các khái niệm](Terminologies.md)

[Công việc dự kiến](TODO.md)

[Cách tham gia đóng góp](Contributing.md)

