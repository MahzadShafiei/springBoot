package com.mahzad.springBoot.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

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
}
