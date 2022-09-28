import java.io.*;
import java.util.*;

public class Individual_1 {
    //利用静态变量储存不同数据
    static int number = 0;//记录生成的算式数量
    static ArrayList<String> str_last = new ArrayList<>();//记录题目最终形式
    static ArrayList<String> answer = new ArrayList<>();//记录答案
    static ArrayList<Integer> hashCode = new ArrayList<>();//记录题目字符串的哈希值,用于防重复
    /**
     * 一个运算符算式储存
     * @param random_first
     * @param random_second
     * @param type
     */
    static void Save(int random_first,int random_second,int type){

        number+=1;
        //储存此次生成的算式的结果
        int ans = 0;
        //根据不同类型进行结果计算
        if (type%2==0)ans=random_first+random_second;
        else ans=random_first-random_second;
        //储存哈希值
        hashCode.add(toString(random_first,random_second,type).hashCode());
        //储存daan
        answer.add(number+"."+ans);
        //储存算式
        str_last.add(number+"."+toString(random_first,random_second,type));
    }
    /**
     * 两个运算符算式储存
     * @param random_first
     * @param random_second
     * @param random_third
     * @param type
     */
    static void Save(int random_first,int random_second,int random_third,int type){

        number+=1;
        //储存此次生成的算式的结果
        int ans = 0;
        //根据不同类型进行结果计算
        switch (type){
            case 0: ans = random_first+random_second+random_third ;break;
            case 1: ans = random_first+random_second-random_third ;break;
            case 2: ans = random_first-random_second-random_third ;break;
            case 3: ans = random_first-random_second+random_third ;break;
        }
        //储存哈希值
        hashCode.add(toString(random_first,random_second,random_third,type).hashCode());
        //储存答案
        answer.add(number+"."+ans);
        //储存算式
        str_last.add(number+"."+toString(random_first,random_second,random_third,type));
    }
    /**
     * 一个运算符算式判断是否满足生成算式条件：100内且不重复
     * @param random_first
     * @param random_second
     * @param type
     * @return
     */
    static boolean Judge(int random_first,int random_second,int type){

        boolean e = true;
        //判断是否在100以内
        if (type%2 == 0) e = e && (random_first+random_second<=100);
        else e = e && (random_first-random_second>=0);
        //利用字符串的哈希值保证算式不重复
        e = e && judgeRep(hashCode,toString(random_first,random_second,type).hashCode());

        return e;
    }
    /**
     * 两个运算符算式判断是否满足条件
     * @param random_first
     * @param random_second
     * @param random_third
     * @param type
     * @return
     */
    static boolean Judge(int random_first,int random_second,int random_third,int type){

        boolean e = true;
        //判断是否在100以内
        int ans = 0;

        switch (type){
            case 0: ans = random_first+random_second+random_third ;break;
            case 1: ans = random_first+random_second-random_third ;break;
            case 2: ans = random_first-random_second-random_third ;break;
            case 3: ans = random_first-random_second+random_third ;break;
        }

        e = e && ans <=100 && ans >=0 && random_first != 0;
        e = e &&  judgeRep(hashCode,toString(random_first,random_second,random_third,type).hashCode());

        return e;
    }
    /**
     * 利用random随机数生成算式
     */
    static void Creat(){
        Random random = new Random();
        //运算符数量
        int random_number = random.nextInt(1,3);
        //运算符类型
        //两个运算符时代表加减不同组合，0-3分别代表++，+-，--，-+，
        // 一个运算符时，偶数+，奇数-
        int random_type = random.nextInt(0,4);
        //生成三个参与运算的随机数
        int random_first = random.nextInt(1,100),
                random_second = random.nextInt(1,100),
                random_third = random.nextInt(1,100);
        //一个运算符算式生成
        if(random_number == 1 && Judge(random_first, random_second,random_type))
            Save(random_first,random_second,random_type);
            //两个运算符算式生成
        else if (Judge(random_first,random_second,random_third,random_type))
            Save(random_first,random_second,random_third,random_type);
    }
    /**
     * 一个运算符算式转为字符串
     * @param firstNumber
     * @param secondNumber
     * @param type
     */
    static String toString(int firstNumber,int secondNumber,int type){

        StringBuffer str = new StringBuffer();

        if (type%2 == 0) str.append(firstNumber+"+"+secondNumber+"=");
        else str.append(firstNumber+"-"+secondNumber+"=");

        return str.toString();
    }
    /**
     * 两个运算符算式转为字符串
     * @param firstNumber
     * @param secondNumber
     * @param thirdNumber
     * @param type
     * @return
     */
    static String toString(int firstNumber,int secondNumber,int thirdNumber,int type){

        StringBuffer str = new StringBuffer();

        switch (type){
            case 0:str.append(firstNumber+"+"+secondNumber+"+"+thirdNumber+"=");break;
            case 1:str.append(firstNumber+"+"+secondNumber+"-"+thirdNumber+"=");break;
            case 2:str.append(firstNumber+"-"+secondNumber+"-"+thirdNumber+"=");break;
            case 3:str.append(firstNumber+"-"+secondNumber+"+"+thirdNumber+"=");break;
        }

        return str.toString();
    }
    /**
     * 判断新算式是否与之前重复
     * @param arr
     * @param code
     * @return
     */
    static boolean judgeRep(ArrayList<Integer> arr,long code){

        boolean e = true;

        if (arr.size() == 0) e = e && (arr.size()==0);//ture表示不重复，false

        else//遍历hashcode集合进行对比
            for (int i = 0;i < arr.size();i++){
                e = e && (code != arr.get(i));
            }

        return e;
    }
    /**
     * 将内容储存进对应文件
     * @param file
     * @param arr
     */
    static void saveFile(String file,ArrayList<String> arr){

        try{

            FileWriter fw = new FileWriter(file);

            for (int i = 0;i < number;i++){
                fw.write(arr.get(i));
                //换行
                fw.write("\n");
            }

            fw.close();
        }

        catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String args[]){

        Scanner in = new Scanner(System.in);

        System.out.println("请问需要生成的算式数目：");
        int N = in.nextInt();
        //算式生成
        for (;number < N;){
            Creat();
        }
        //两个文件路径
        String file_exercises =  "D:\\IntelliJ IDEA 2021.3.2\\bin\\Github\\Exercises.txt";
        String file_answers = "D:\\IntelliJ IDEA 2021.3.2\\bin\\Github\\Answers.txt";
        //算式和答案分别储存到文件
        saveFile(file_exercises,str_last);
        saveFile(file_answers,answer);
    }
}
