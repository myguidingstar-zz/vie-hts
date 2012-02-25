Giới thiệu
==========
Chương trình này được viết bằng clojure, mục đích chủ yếu để tạo danh sách các âm cần ghi.
Ngoài ra, chương trình cũng đưa ra một số kết quả tính toán về mặt số lượng.

Các khái niệm
=============
Từ
---

1. **Từ đầy đủ** là từ có thể đọc lên được theo cách đọc tiếng Việt.
	* ví dụ:	"từ", "đầy", "đủ", "chương", "trình" là các từ đầy đủ.
	* "tiế" không phải là từ đầy đủ.
* **Vần** (trong "đánh vần", "vần thơ") là phần còn lại của một từ đầy đủ sau khi đã bỏ phần phụ âm đầu (nếu có).
* **Phần phụ âm** đầu này có thể là một phụ âm đơn hoặc nhóm phụ âm.
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
