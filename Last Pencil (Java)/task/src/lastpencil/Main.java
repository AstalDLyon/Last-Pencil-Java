package lastpencil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("How many pencils would you like to use: ");
        int pencilQuantity = pencilGameRules(bufferedReader);
        ArrayList<String> names = new ArrayList<>();
        names.add("John");
        names.add("Jack");

        System.out.println("Who will be the first (John, Jack): ");
        int controller = whoWillBeFirst(bufferedReader, names);
        int botIndex = 1;
        String winner = playGame(controller, pencilQuantity, names, bufferedReader,botIndex);
        System.out.println(winner);
        bufferedReader.close();
    }
    public static  Integer pencilGameRules(BufferedReader bufferedReader){
        while (true) {
            try {
                String input = bufferedReader.readLine().trim();
                int pencilQuantity = Integer.parseInt(input);
                if (pencilQuantity <= 0){
                    System.out.println("The number of pencils should be positive");
                    continue; // para voltar pro loop
                }
                return pencilQuantity;
            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
            } catch (IOException e) { // erro de IO do bufferedReader está sendo tratado dentro do método
                System.out.println("Error while reading the input.");
            }
        }
    }
    public static int whoWillBeFirst(BufferedReader bufferedReader, ArrayList names){
        while (true) {
            try {
                String first = bufferedReader.readLine().trim();
                if (first.equals(names.get(0))){
                    return 0;
                } else if (first.equals(names.get(1))) {
                    return 1;
                } else {
                    System.out.println("Choose between " + names.get(0) + " and " + names.get(1));
                    continue; // volta pro loop
                }
            } catch (IOException e) {
                System.out.println("Error while reading the input."); // // erro de IO do bufferedReader está sendo tratado dentro do método
            }
        }
    }

    public static String playGame(int controller,int pencilQuantity, ArrayList names, BufferedReader bufferedReader,int botIndex){
        String pencil = "|";
        while (pencilQuantity != 0){
            System.out.println(pencil.repeat(pencilQuantity));

            // Exemplo: "John's turn!" ou "Jack's turn:" conforme o padrão pedido
            if (controller == botIndex) {
                System.out.println(names.get(controller) + "'s turn:");
                pencilQuantity = botTurn(pencilQuantity);
            } else {
                System.out.println(names.get(controller) + "'s turn!");
                pencilQuantity = pencilAlteration(pencilQuantity, bufferedReader);
            }

            // Alterna o turno entre 0 e 1
            controller = (controller == 0) ? 1 : 0;
        }
        return names.get(controller) + " won!";
    }
    public static int  pencilAlteration(int pencilQuantity, BufferedReader bufferedReader){
        while (true){
            try {
                int pencilToRemove = Integer.parseInt(bufferedReader.readLine().trim());
                if ((pencilToRemove > 3) || (pencilToRemove <= 0)){
                    System.out.println("Possible values: '1', '2', or '3'");
                    continue;
                } else if (pencilToRemove > pencilQuantity){
                    System.out.println("Too many pencils were taken");
                    continue;
                } else {
                    pencilQuantity -= pencilToRemove;
                    return pencilQuantity;
                }
            } catch (IOException e){
                System.out.println("Error while reading the input.");
            } catch (NumberFormatException e){
                System.out.println("Possible values: '1', '2', or '3'");
            }
        }
    }
    public static int botTurn(int pencilQuantity) {
        Random random = new Random();
        int pencilsTaken;
        int remainder = pencilQuantity % 4;

        if (pencilQuantity == 1) {
            pencilsTaken = 1;
        } else if (remainder == 0) {
            pencilsTaken = 3;
        } else if (remainder == 3) {
            pencilsTaken = 2;
        } else if (remainder == 2) {
            pencilsTaken = 1;
        } else {
            pencilsTaken = random.nextInt(Math.min(pencilQuantity, 3)) + 1;
        }

        System.out.println(pencilsTaken);
        return pencilQuantity - pencilsTaken;
    }
}
