package com.mahzad.springBoot.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.Semaphore;

@Service
public class ThreatService {
    static Semaphore sem1 = new Semaphore(1);
    static Semaphore sem2 = new Semaphore(0);
    static Semaphore sem3 = new Semaphore(0);

    public ThreatService() {}

    public void getResultInThreat()
    {
        new Thread(()->readFile("file1.txt", sem1, sem2,1)).start();
        new Thread(()->readFile("file2.txt", sem2, sem3,2)).start();
        new Thread(()->readFile("file3.txt", sem3, sem1,3)).start();
    }

    public void readFile(String fileName, Semaphore myTurn, Semaphore nextTurn, int fileType)
    {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            while (true) {
                myTurn.acquire();
                String line = br.readLine();
                if(line == null)
                    break;

                String lineToPrint = line;
                if(fileType == 1)
                    lineToPrint += " + ";
                else if(fileType == 2)
                    lineToPrint += " = ";

                if(fileType == 3)
                    System.out.println(lineToPrint);
                else
                    System.out.print(lineToPrint);

                nextTurn.release();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
