package com.company;

import java.util.concurrent.Exchanger;


public class Main {
    public static void main(String[] args) {

        Exchanger<Integer> ex = new Exchanger<Integer>();
        new Thread(new PutThread(ex)).start();
        new Thread(new GetThread(ex)).start();
    }
}

class PutThread implements Runnable{

    Exchanger<Integer> exchanger;
    Integer message;

    PutThread(Exchanger<Integer> ex){

        this.exchanger=ex;
        message = 1;
    }
    public void run(){
        int count = 0;
        int maxcount = 10;//сколько простых чисел надо найти
        Integer start = 5;//откуда начинать
        boolean k = true;
        try{
            while(count<maxcount)
            {
                for(int i = 2;i<start;i++)
                {
                    if(start%i == 0){
                        k = false;
                        break;
                    }
                }
                if(k){
                    exchanger.exchange(start);
                    count ++;
                }
                start++;
                k = true;
            }
        }
        catch(InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
}
class GetThread implements Runnable{

    Exchanger<Integer> exchanger;
    Integer message;

    GetThread(Exchanger<Integer> ex){
        this.exchanger=ex;
        message = 2;
    }
    public void run(){

        try{
            for(int i = 0; i<10;i++){
                message = exchanger.exchange(i);
                System.out.println("GetThread has received: " + message);
            }
        }
        catch(InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
}