Giới thiệu
==========
Chương trình này được viết bằng clojure, mục đích chủ yếu để tạo danh sách các âm cần ghi.
Ngoài ra, chương trình cũng đưa ra một số kết quả tính toán về mặt số lượng.

Về kết quả cuối cùng của dự án, đó là có được một chương trình đọc văn bản tiếng Việt (Vietnamese Text-to-Speech) với chất lượng giọng nói đầu ra đủ tốt.
Giải pháp sẵn có hiện nay là espeak, chương trình nhỏ gọn đang được google sử dụng để phát âm tiếng Việt.

Để máy tính đọc được tiếng Việt, có một số hướng đi:

* Ghi âm từng từ có nghĩa một (kiểu wyabdc-real-people-tts)
	* Ưu: chất lượng đầu ra kiểm soát được 100%
	* Nhược:
		* không/khó mở rộng số từ: khi phát sinh từ mới (đặc điểm cố hữu của ngôn ngữ) phải ghi âm thêm; người dùng phải cập nhật phiên bản)
		* số lượng từ khổng lồ
		
* Tổng hợp từ các đơn vị âm cơ bản (cách của espeak), dùng thuật toán để sinh ra âm
	* Ưu:
		* đọc được tất cả các từ mà không cần cho sẵn
		* có thể điều chỉnh giọng đọc lên giọng, xuống giọng v.v... bằng tham truyền đến thuật toán.
	* Nhược: vì quá trình phân tích xử lí phức tạp nên đóng góp của cộng đồng sẽ chủ yếu là đánh giá kết quả, còn lại cá nhân sẽ nghiên cứu điều chỉnh.
* Cách thực hiện của dự án:
	* Ghi âm các **phần phụ âm đầu** và **vần** rồi tổng hợp (đơn giản là nối). Các khái niệm vui lòng xem bên dưới.
	* Kết hợp cân bằng (nhưng không thể tối đa) ưu, nhược của hai phương pháp trên.

Các khái niệm
=============
Từ
---

1. **Từ đầy đủ** là từ có thể đọc lên được theo cách đọc tiếng Việt.
    * ví dụ:    "từ", "đầy", "đủ", "chương", "trình" là các từ đầy đủ.
    * "tiế" không phải là từ đầy đủ.
* **Vần** (trong "đánh vần", "vần thơ") là phần còn lại của một từ đầy đủ sau khi đã bỏ phần phụ âm đầu (nếu có).
* **Phần phụ âm đầu** này có thể là một phụ âm đơn hoặc nhóm phụ âm.
    * ví dụ:
        * "ừ", "ầy", "ủ", "ương", "ình" v.v... là các vần trong ví dụ trên.
        * "t", "đ", "đ", "ch", "tr"
    * để trực quan, từ nay sẽ kí hiệu thành:
        * ~ừ ~ầy ~ủ
        * t~ ch~ tr~

Âm
---

1. Mỗi vần đều có một nguyên âm làm **âm chính**
    * ví dụ:
        * "từ" -> ~ừ -> "ư"
        * "đầy" -> ~ầy -> "â"
        * "chương" -> ~ương -> "ơ"
    * kí hiệu:
        * /ừ /ầ /ơ
* Ngoài **âm chính**, vần còn có thể có **dấu**, **đầu vần** và **đuôi vần**
    * ví dụ:
        * "đầy" -> ~ầy -> **dấu** *huyền*, không có **đầu vần** và **đuôi vần** là *y*
        * "chương" -> ~ương -> **dấu** *thanh* (còn gọi là không dấu), **đầu vần** *ư* và **đuôi vần** là *ng*
    * kí hiệu:
        * đầy = đ~ _ /â {f} -y
        * đủ = đ~ _ /u {r} _
        * chương = ch~ ư- /ơ {_} -ng
        * đủ = đ~ _ /u {r} _
    * có thể thấy:
        * dấu _ đại diện cho *không có gì*
        * kí hiệu của dấu {_}, {f}, {s}, {r}, {x}, {j} mượn từ cách gõ telex; 
* Vì mục đích chủ yếu để thống kê, mà mỗi vần chỉ có thể có 2 hoặc 6 cách kết hợp dấu nên có thể dùng cách kí hiệu sau:
    * số cách kết hợp dấu phụ thuộc vào **đuôi vần**
        * {2} với -c -ch -p -t
        * {6} với -_ -m -n -ng -nh -i -o -u -y
    * ví dụ:
        * _ /a {2} **-ch** = ~ách, ~ạch
        * ư- /ơ {6} **-ng** = ~ương ~ường ~ướng ~ưởng ~ưỡng ~ượng
* Quy ước:
    * xoong = x~ o- /o {_} -ng
    * hươu = h~ ư- /ơ {_} -u

Cách thống kê
-------------
