import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Number of inputs to accept:");
        int x = input.nextInt();

        int[] inputnum = new int[x];
        System.out.println("Enter input" + x + "numbers:");
        for (int i = 0; i < x; i++) {
            inputnum[i] = input.nextInt();
        }

        usingbubbleSort(inputnum);
        System.out.println("Sorted input:");
        for (int i=0; i<inputnum.length; i++) {
            System.out.println(inputnum[i]);
        }
    }
    public static void usingbubbleSort(int[] arr) {
        int x = arr.length;
        int temp;
        for (int i=0; i<x; i++) {
            for (int j=1; j<(x-i); j++) {
                if (arr[j-1] > arr[j]) {
                    temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}