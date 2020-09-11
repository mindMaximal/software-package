package com.labs;

import java.util.ArrayList;

public class Summator extends Thread{
    //Флаг для остановки потока
    private boolean stopped = false;
    //Сумма чисел
    private double sum = 0;
    //Буфер
    private final ArrayList<Double> buffer;

    //Получаем буффер в конструкторе
    public Summator(Generator generator) {
        buffer = generator.getBuffer();
    }

    //Метод возращающий значение поля sum
    public Double getSum() {
        return sum;
    }

    //Метод, останавливающий поток
    public  void stopThread() {
        stopped = true;
    }

    //Перегружаем стандартный метод
    @Override
    public void run() {

        while (!stopped) synchronized (buffer) {
            //Пока буфер пустой
            while (buffer.isEmpty()) {
                try {
                    //Освобождаем монитор переводит вызывающий поток в состояние ожидания до тех пор, пока другой поток не вызовет метод notify()
                    buffer.wait();
                } catch (InterruptedException e) {
                    System.out.println("Второй поток был остановлен");
                }
            }

            //Считаем сумму чисел в буфере
            for (Double num : buffer) {
                sum += num;
            }

            System.out.printf("2-й поток работает: сумма = %s%n", sum);

            //Очищаем буфер
            buffer.clear();
            //Продолжаем работу пока, у которого ранее был вызван метод wait()
            buffer.notify();
        }

    }
}
