package com.mahzad.springBoot.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Random;

@Service
public class ThreadService {
    static int fileTurnType = 1;

    public ThreadService() {}

    public void getResultInThreat()
    {
        new Thread(()->readFile("file1.txt", 1,2)).start();
        new Thread(()->readFile("file2.txt", 2,3)).start();
        new Thread(()->readFile("file3.txt", 3,1)).start();
    }

    public void readFile(String fileName, int fileType, int nextFileTurnType)
    {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while ((line = br.readLine())!=null) {

                while (fileTurnType != fileType)
                    Thread.yield();

                String lineToPrint = line;
                if(fileType == 1)
                    lineToPrint += " + ";
                else if(fileType == 2)
                    lineToPrint += " = ";

                if(fileType == 3)
                    System.out.println(lineToPrint);
                else
                    System.out.print(lineToPrint);

                fileTurnType = nextFileTurnType;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private final Object lock = new Object();
    public void getSumOfDigits()
    {
        new Thread(()->getNextNumber("Consumer1: ")).start();
        new Thread(()->getNextNumber("Consumer2: ")).start();
        new Thread(()->addNumber()).start();
    }
    private void getNextNumber(String consumerName)
    {
            Path path = Path.of("file2-1.txt");
            try  {
                while (true) {
                    Integer mainNumber =0;
                    synchronized (lock) {
                        List<String> lines = Files.readAllLines(path);
                        if (lines.isEmpty())
                            return;

                        mainNumber = Integer.parseInt(lines.get(0));
                        lines.remove(0);
                        Files.write(path, lines);
                    }
                    int sum = 0;
                    Integer number =mainNumber;
                    while (number > 0) {
                        sum += number % 10;
                        number /= 10;
                    }
                    System.out.println(consumerName + mainNumber + " Sum: " + sum);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void addNumber()
    {
        Path path = Path.of("file2-1.txt");
        Random random = new Random();
        long endTime = System.currentTimeMillis() + 2000;
        try  {
            while (System.currentTimeMillis() < endTime) {
                Integer number =random.nextInt(1001);
                synchronized (lock) {
                    List<String> lines = Files.readAllLines(path);
                    lines.add(String.valueOf(number));
                    Files.write(path, lines);
                }
            }
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
