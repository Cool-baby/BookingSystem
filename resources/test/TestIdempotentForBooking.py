# -*- coding: utf-8 -*-
"""
@Project ：testnengyuyue 
@File ：TestIdempotentForBooking.py
@Author ：Hao
@Date ：2023-11-11 011 15:54 
@Describe ：测试Booking接口幂等性
"""
import requests
import threading
import time

# 全局变量用于存储请求完成时间
request_times = []
request_record = []
# 全局变量用于记录成功的请求数
successful_requests = 0
failed_requests = 0
error_requests = 0


def make_request(url, user_id, request_data):
    """
    发起Http Post请求
    :param url: 请求地址
    :param user_id: 模拟token
    :param request_data: 请求体
    """
    global successful_requests
    global failed_requests
    global error_requests
    # 请求头
    request_header = {
        "userId": str(user_id)
    }
    try:
        start_time = time.time()
        response = requests.post(url, headers=request_header, json=request_data)
        end_time = time.time()
        elapsed_time = end_time - start_time
        request_times.append(elapsed_time)
        response_json = response.json()  # 响应转json并存储
        request_record.append(response_json)
        # 可以根据需要处理响应内容或状态码
        if response_json['code'] == 20001:
            successful_requests += 1
        elif response_json['code'] == 20000:
            failed_requests += 1
        elif response_json['code'] == 20002:
            error_requests += 1
    except Exception as e:
        print(f"Error making request: {e}")


def run_test(url, num_requests, user_id, request_data):
    """
    多线程发起请求
    :param url: 请求地址
    :param num_requests: 线程数量，相当于请求数量
    :param user_id: 模拟token中携带的user ID
    :param request_data: 请求体
    """
    # 创建线程池
    threads = []

    # 启动指定数量的线程，每个线程发起一个HTTP请求
    for _ in range(num_requests):
        thread = threading.Thread(target=make_request, args=(url, user_id, request_data, ))
        threads.append(thread)
        thread.start()

    # 等待所有线程完成
    for thread in threads:
        thread.join()


if __name__ == "__main__":
    # 设置要测试的URL和请求数量
    target_url = "http://localhost:8080/maneuvers/booking"
    # 线程数量
    num_requests = 1000
    # 用户ID
    user_id = 20210007
    # 请求体
    request_data = {
        "maneuverId": "01bfa6d6dbcf45f8805461f11c4fccb6",
        "segmentId": 10019
    }

    # 运行压力测试
    run_test(target_url, num_requests, user_id, request_data)

    # 计算压测指标
    total_requests = len(request_times)
    total_time = sum(request_times)
    qps = total_requests / total_time if total_time > 0 else 0

    for _ in request_record:
        print(_)

    print("-------------")
    print(f"Total Requests: {total_requests}")
    print(f"Success Times: {successful_requests}, Failed Times: {failed_requests}, Error Times: {error_requests}")
    print(f"Success Ratio: {successful_requests / total_requests},"
          f" Failed Ratio: {failed_requests / total_requests},"
          f" Error Ratio: {error_requests / total_requests}")
    print(f"Total Time: {total_time:.4f} seconds")
    print(f"QPS: {total_time:.4f}")
