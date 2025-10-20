// k6 run k6-test/order-test-single-token.js

import http from "k6/http";
import { check, sleep } from "k6";

const LOGIN_URL = "http://localhost:8888/api/v1/identity/auth/token";
const ORDER_URL = "http://localhost:8888/api/v1/order/create";

// Lấy token 1 lần trước khi test (setup chạy một lần)
export function setup() {
    const creds = { username: "admin", password: "admin" };
    const res = http.post(LOGIN_URL, JSON.stringify(creds), {
        headers: { "Content-Type": "application/json" },
    });
    if (res.status !== 200) {
        console.error("Login failed", res.status, res.body);
        return { token: null };
    }
    const token = res.json("result").token;
    return { token };
}
// 🧩 Cấu hình test
export const options = {
    vus: 100, // 10 user ảo chạy song song
    iterations: 1000, // Tổng cộng 100 request
    // Hoặc có thể dùng duration:
    // duration: '30s', // chạy trong 30 giây
};

export default function (data) {
    const token = data.token;
    if (!token) {
        return;
    }

    const payload = JSON.stringify({
        userId: "fe633830-da03-47f0-b4d1-c21e74e5bf91",
        orderDate: new Date().toISOString(),
        status: "PENDING",
        totalAmount: 500000,
        paymentType: "PREPAID",
        address: "123 Main St, City, Country",
        listItemDetail: [
            {
                productId: "8b0e6d3c-b25d-48c4-a203-a55a79a92698",
                quantity: 1,
                priceAtTime: 10000000,
            },
        ],
    });

    const params = {
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
    };

    const res = http.post(ORDER_URL, payload, params);

    check(res, {
        "status is 200 or 201": (r) => r.status === 200 || r.status === 201,
    });

    // Tùy chỉnh sleep nếu muốn
    sleep(0.1);
}
