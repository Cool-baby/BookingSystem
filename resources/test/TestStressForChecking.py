# -*- coding: utf-8 -*-
"""
@Project ：testnengyuyue 
@File ：TestStressForBooking.py
@Author ：Hao
@Date ：2023-11-10 010 11:13 
@Describe ： 查询业务压力测试
"""
import requests
import threading
import time

# 全局变量用于存储请求完成时间
request_times = []

# 全局变量用于记录成功的请求数
successful_requests = 0
failed_requests = 0
error_requests = 0


def make_request(url):
    """
    发起Http Get请求
    :param url: 请求地址
    """
    global successful_requests
    global failed_requests
    global error_requests
    try:
        start_time = time.time()
        response = requests.get(url)
        end_time = time.time()
        elapsed_time = end_time - start_time
        request_times.append(elapsed_time)
        # 可以根据需要处理响应内容或状态码
        if response.json()['code'] == 20001:
            successful_requests += 1
        elif response.json()['code'] == 20000:
            failed_requests += 1
        elif response.json()['code'] == 20002:
            error_requests += 1
    except Exception as e:
        print(f"Error making request: {e}")


def run_test(url, num_requests):
    """
    多线程发起请求
    :param url: 请求地址
    :param num_requests: 线程数量，相当于请求数量
    """
    # 创建线程池
    threads = []

    # 启动指定数量的线程，每个线程发起一个HTTP请求
    for _ in range(num_requests):
        thread = threading.Thread(target=make_request, args=(url, ))
        threads.append(thread)
        thread.start()

    # 等待所有线程完成
    for thread in threads:
        thread.join()


if __name__ == "__main__":
    # 设置要测试的URL和请求数量
    target_url = "http://localhost:8080/maneuvers/7d7b63a896754161b05bf1aa50d079e1"
    # 线程数量
    num_requests = 1000

    # 运行压力测试
    run_test(target_url, num_requests)

    # 计算压测指标
    total_requests = len(request_times)
    total_time = sum(request_times)
    qps = total_requests / total_time if total_time > 0 else 0

    print(f"Total Requests: {total_requests}")
    print(f"Success Times: {successful_requests}, Failed Times: {failed_requests}, Error Times: {error_requests}")
    print(f"Success Ratio: {successful_requests / total_requests},"
          f" Failed Ratio: {failed_requests / total_requests},"
          f" Error Ratio: {error_requests / total_requests}")
    print(f"Total Time: {total_time} seconds")
    print(f"QPS: {qps}")

