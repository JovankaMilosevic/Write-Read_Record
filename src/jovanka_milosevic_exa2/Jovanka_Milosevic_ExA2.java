package jovanka_milosevic_exa2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a console based app that can be used to read data from the record in the given file.
 *
 * @author Jovanka Milosevic
 */
public class Jovanka_Milosevic_ExA2 {

    private static final int NAME_SIZE = 15;
    private static final int ID_SIZE = 4;
    private static final int OWING_SIZE = 8;

    private static final int RECORD_SIZE
            = (NAME_SIZE * 2) + ID_SIZE + OWING_SIZE;

    private static int selection;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        do {
            Scanner input = new Scanner(System.in);
            int choice = validateWhatToGetSelection();
            selection = choice;

            if (choice == 1) {
                readFile(input);
            } else if (choice == 2) {
                readFile(input);
            } else if (choice == 3) {
                readFile(input);
            } else {
                break;
            }
        } while (true);
    }

    /**
     * A method that is used for padding. If the number of characters is less than 15, the field will be padded. If it
     * is bigger than 15, it will be truncated.
     *
     * Author of this method is Prof. Paul Bonenfant
     *
     * @param name - entered name
     * @return - name, String
     */
    public static String padName(String name) {
        if (name.length() > NAME_SIZE) {
            name = name.substring(0, NAME_SIZE);
        } else {
            String format = "%-" + NAME_SIZE + "s";
            name = String.format(format, name);
        }
        return name;
    }

    /**
     * This method reads a file, the selected part of the given file.
     *
     * @param input - Scanner input
     */
    private static void readFile(Scanner input) {
        int choice = validateRecordSelection();

        RandomAccessFile raf;

        try {
            raf = new RandomAccessFile("students.dat", "r");
            readRecord(raf, choice);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * This method reads the specified part of the record.
     *
     * @param raf - a file to read
     * @param choice - inserted number
     * @throws IOException
     */
    private static void readRecord(RandomAccessFile raf, int choice) throws IOException {
        int id;
        double owing;
        raf.seek((choice - 1) * RECORD_SIZE);
        if (selection == 1) {
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < NAME_SIZE; i++) {
                name.append(raf.readChar());
            }
            String format = "Name is: %s\n";
            System.out.printf(format, name.toString().trim());
        } else if (selection == 2) {
            raf.skipBytes(NAME_SIZE * 2);
            id = raf.readInt();
            String format = "Id is: %d\n";
            System.out.printf(format, id);
        } else if (selection == 3) {
            raf.skipBytes((NAME_SIZE * 2) + ID_SIZE);
            owing = raf.readDouble();
            String format = "Owing is: $%.2f\n";
            System.out.printf(format, owing);
        }
    }

    /**
     * This method validates input that user entered after he was asked what he wants to get.
     *
     * @return choice, int
     */
    public static int validateWhatToGetSelection() {
        while (true) {
            try {
                System.out.println("Enter what to get:");
                System.out.println("1 for name");
                System.out.println("2 for id");
                System.out.println("3 for owing");
                System.out.println("0 to exit");
                Scanner input = new Scanner(System.in);

                int choice = input.nextInt();
                input.nextLine();

                if (choice >= 0 && choice <= 3) {
                    return choice;
                } else {
                    System.out.println("Please, enter a valid number.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Please, enter a number.");
            }
        }
    }

    /**
     * This method validates input that user entered after he was asked which record he wants to select.
     *
     * @return = choice, int
     */
    public static int validateRecordSelection() {
        while (true) {
            try {
                System.out.print("Enter which record (1, 2 or 3): ");
                Scanner input = new Scanner(System.in);

                int choice = input.nextInt();
                input.nextLine();

                if (choice >= 1 && choice <= 3) {
                    return choice;
                } else {
                    System.out.println("Please, enter a valid number.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Please, enter a number.");
            }
        }
    }
}
