# 🎬 Smart Search API

API cho phép khởi tạo vector DB và thực hiện tìm kiếm phim tương tự (Semantic Search) bằng cách sử dụng Spring AI +
Vector Store (như pgvector).

---

## 📌 Base URL

```
http://localhost:8080
```

---

## 🚀 Endpoints

### 1. POST `/init`

Khởi tạo vector database từ danh sách phim hiện tại trong hệ thống.

- **Mục đích**: Vector hóa dữ liệu phim để phục vụ cho semantic search.
- **Request**: `POST` không cần body.
- **Response**: Trả về chuỗi `"ok"` nếu thành công.

#### ✅ Ví dụ curl:

```bash
  curl -X POST http://localhost:8080/init
```

---

### 2. GET `/search-movie`

Tìm kiếm các phim có nội dung tương tự với từ khoá của người dùng.

- **Mục đích**: Trả về danh sách phim liên quan nhất đến câu hỏi/ngữ nghĩa.
- **Request**:
    - Method: `GET`
    - Body: chuỗi câu truy vấn (ví dụ: `"phim có diễn viên Johnny Depp"`)
- **Response**: Danh sách `MovieDto` (ID, name, mainLeads, description)

#### ✅ Ví dụ curl:

```bash
  curl -X GET http://localhost:8080/search-movie \
     -H "Content-Type: application/json" \
     -d "\"phim võ thuật hài của Thành Long\""
```

---

### 3. GET `/search-movie-top`

Tìm kiếm phim tương tự nhưng giới hạn kết quả trả về (ví dụ: top 2 kết quả).

- **Mục đích**: Tìm phim gần nhất với truy vấn, chỉ lấy một số lượng nhất định.
- **Request**:
    - Method: `GET`
    - Body: chuỗi câu truy vấn (String)
- **Response**: Danh sách `MovieDto` (tối đa 2 phần tử)

#### ✅ Ví dụ curl:

```bash
  curl -X GET http://localhost:8080/search-movie-top \
     -H "Content-Type: application/json" \
     -d "\"phim hành động Mỹ năm 2000\""
```

---

## 🧾 MovieDto cấu trúc (response object)

```json
[
  {
    "id": 1,
    "name": "Tên phim",
    "mainLeads": "Tên diễn viên chính",
    "description": "Mô tả nội dung phim"
  }
]
```

---
