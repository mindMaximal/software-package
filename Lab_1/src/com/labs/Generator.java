package com.labs;

import java.util.ArrayList;

public class Generator extends Thread {
    //Размер буфера
    private final int N = 3;
    //Буфер
    private final ArrayList<Double> buffer = new ArrayList<>();

    //Метод получения буфера
    public ArrayList<Double> getBuffer() {
        return buffer;
    }
    //Метод проверки заполненности буфера
    public boolean bufferIsFull() {
        return buffer.size() == N;
    }

    //Перегружаем стандартный метод
    @Override
    public void run() {

        double x = 0.01;
        double num = 0;
        //Заполняем буфер числами
        for (int i = 0; i < 1000; i++) {

            //Синхронизируем потоки
             synchronized(buffer) {

                 //Пока буфер полный
                 while (bufferIsFull()) {
                     try {
                         //Освобождаем монитор переводит вызывающий поток в состояние ожидания до тех пор, пока другой поток не вызовет метод notify()
                         buffer.wait();
                     } catch (InterruptedException e) {
                         System.out.println("Первый поток был остановлен");
                     }
                 }

                 //Расчитываем необходимый ряд чисел
                 num = Math.sin( x / Math.PI );
                 //Добавляем числов в ряд
                 buffer.add(num);

                 x += 0.01;

                 System.out.printf("Число номер:%d равно: %s%n", i + 1, num);
                 //Продолжаем работу пока, у которого ранее был вызван метод wait()
                 buffer.notify();

             }

        }

    }
}
