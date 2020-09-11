package com.labs;

/*

    Значения констант и реализуемые потоками функции:
   - N=3
   - Первый поток - генерирует в буфер 1000 чисел ряда sin(x/Pi), где x принимает значения от 0,01 до 10 с шагом 0,01.
   - Второй поток - Накапливает сумму чисел в буфере и по окончанию доступных чисел в буфере выводит полученную сумму на экран.
    Буфер очищается вторым потоком на каждой итерации.

*/

public class Main {

    public static void main(String[] args) {

        Generator generator = new Generator();
        Summator summator = new Summator(generator);

        generator.start();
        summator.start();

        try {
            generator.join();
        } catch (InterruptedException e) {
            System.out.println("Поток был остановлен");
        }

        generator.interrupt();
        summator.interrupt();
        summator.stopThread();

        System.out.printf("Итоговая сумма: %s%n", summator.getSum());

    }
}
