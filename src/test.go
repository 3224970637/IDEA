package main

import (
	"fmt"
	"runtime"
	"sync"
	"time"
)

func main() {
	// 获取CPU核心数
	numCPU := runtime.NumCPU()
	fmt.Printf("Number of CPU cores: %d\n", numCPU)

	// 设置使用的CPU核心数
	runtime.GOMAXPROCS(numCPU)

	// 创建等待组
	var wg sync.WaitGroup

	// 创建通道用于传递结果
	resultCh := make(chan string, numCPU)

	// 启动多个goroutine并发执行任务
	for i := 1; i <= numCPU; i++ {
		wg.Add(1)
		go performTask(i, &wg, resultCh)
	}

	// 在主goroutine中等待所有goroutine完成
	go func() {
		wg.Wait()
		close(resultCh)
	}()

	// 从通道中读取结果
	for result := range resultCh {
		fmt.Println(result)
	}
}

func performTask(id int, wg *sync.WaitGroup, resultCh chan<- string) {
	defer wg.Done()

	fmt.Printf("Task %d started\n", id)
	time.Sleep(time.Second * 2) // 模拟一些工作
	resultCh <- fmt.Sprintf("Task %d completed", id)
}
