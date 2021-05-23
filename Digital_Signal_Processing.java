import java.util.*;

public class Digital_Signal_Processing {
    public static void main(String[] args) {

        Scanner Input = new Scanner(System.in);
        List<Integer> array = new ArrayList<>();
        List<Integer> array2 = new ArrayList<>();
        System.out.println("Enter samples for Input Signal X(n): ");
        String Catch = Input.next();
        String[] str = Catch.split(",");

        int i;
        Iterator<String> Catch_Over = Arrays.stream(str).iterator();

        while (Catch_Over.hasNext()) {
            array.add(Integer.parseInt(Catch_Over.next()));

        }
        Iterator<Integer> Catch_Notch;
        System.out.println("Enter the Block Length: ");
        int Block_length = Input.nextInt();
        System.out.println("Enter the Frequency Response of the System: ");
        String Freq = Input.next();

        String[] Freq_Builder = Freq.split(",");
        int m = Freq_Builder.length;
        int l = Block_length - m + 1;
        Iterator<String> Freq_iterate = Arrays.stream(Freq_Builder).iterator();
        while (Freq_iterate.hasNext()) {
            array2.add(Integer.parseInt(Freq_iterate.next()));
        }
        if (array2.size() <= Block_length) {
            i = 0;
            while (i < Block_length - m) {
                array2.add(0);
                i++;
            }
            int iteration;
            if (array.size() % l == 0) {
                iteration = array.size() / l;
            } else {
                iteration = (array.size() / l) + 1;
                i = 0;
                int size = array.size();
                while (i < l - (size % l)) {
                    array.add(0);
                    i++;
                }
            }

            swap(array2);
            int Final_length = (Block_length * iteration) - (iteration - 1) * (m - 1);
            int[] Final_array = new int[Final_length];
            Calculation(iteration, Final_array, 0, array, array2, l, Block_length, 0, 0);
            System.out.println("samples for Output Signal Y(n) : "+ Arrays.toString(Final_array));
            System.out.println(" Total no. of Samples: " + Final_array.length);
        } else {
            System.out.println("Error: ");
        }


    }


    public static void Calculation(int k, int[] Final_array, int Iteration, List<Integer> array, List<Integer> Response_array, int l, int Block_Length, int Start, int I) {
        if (Iteration < k) {
            List<Integer> list = new ArrayList<>();
            int[] Matrix = new int[Block_Length];
            int i = Start;
            int p = 0;
            while (i < l + Start) {
                Matrix[p] = array.get(i);
                i++;
                p++;
            }
            while (p < Block_Length - l) {
                Matrix[p] = 0;
                p++;
            }

            for (i = 0; i < Matrix.length; i++) {
                int Sum = 0;
                if (i == 0) {
                    int j = 0;
                    while (j < Matrix.length) {
                        Sum += Matrix[j] * Response_array.get(j);
                        j++;
                    }

                } else {
                    Swap2(Matrix);

                    int j = 0;
                    while (j < Matrix.length) {
                        Sum += Matrix[j] * Response_array.get(j);
                        j++;
                    }
                }
                list.add(Sum);


            }
            swap(list);
            Iteration++;
            int j = Final_List(Final_array, list, l, Block_Length, I);
            Calculation(k, Final_array, Iteration, array, Response_array, l, Block_Length, (l + Start), j);
        }


    }

    public static int Final_List(int[] Final_array, List<Integer> list, int l, int Block_Length, int i) {

        if (i < Block_Length) {

            while (i < Block_Length) {
                Final_array[i] = list.get(i);
                i++;
            }

            return i;
        } else {
            int j = 0;
            i = i - ((Block_Length - l));
            int k = i + list.size();
            while (i < k) {
                Final_array[i] += list.get(j);
                j++;
                i++;
            }

            return i;

        }

    }

    public static List<Integer> swap(List<Integer> array2) {
      /*  ListIterator<Integer> Reverse= Swap.listIterator();
        Swap.
        */
        int i = 0;
        while (i < array2.size() / 2) {
            int temp = array2.get(i);
            array2.set(i, array2.get(array2.size() - 1 - i));
            array2.set(array2.size() - 1 - i, temp);
            i++;
        }
        return array2;
    }

    public static int[] Swap2(int[] Matrix) {
        int temp = Matrix[Matrix.length - 1];
        int j = Matrix.length - 1;
        while (j > 0) {
            Matrix[j] = Matrix[j - 1];
            j--;
        }
        Matrix[j] = temp;
        return Matrix;
    }

}
