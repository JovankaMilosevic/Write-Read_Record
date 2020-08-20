package jovanka_milosevic_exa2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a console app that can be used to change the specified data in the record of the given file.
 *
 * @author Jovanka Milosevic
 */
public class Jovanka_Milosevic_ExA2_Edit {

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
            int choice = validateWhatToChangeSelection();
            selection = choice;

            if (choice == 1) {
                changeRecord();
            } else if (choice == 2) {
                changeRecord();
            } else if (choice == 3) {
                changeRecord();
            } else {
                break;
            }
        } while (true);
    }

    /**
     * * A method that is used for padding. If the number of characters is less than 15, the field will be padded. If
     * it is bigger than 15, it will be truncated.
     *
     * Author of this method is Prof. Paul Bonenfant
     *
     * @param name
     * @return name, String
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
     * This method changes the value of the selected part of the file.
     */
    private static void changeRecord() {
        int choice = validateRecordSelection();

        String name;
        int id;
        double owing;

        try (RandomAccessFile raf = new RandomAccessFile("students.dat", "rw")) {
            Scanner input = new Scanner(System.in);
            raf.seek((choice - 1) * RECORD_SIZE);
            if (selection == 1) {
                System.out.print("Enter new value for name: ");

                name = input.nextLine();
                raf.writeChars(padName(name));
                readFile();
            } else if (selection == 2) {
                System.out.print("Enter new value for id: ");
                id = input.nextInt();
                raf.skipBytes(NAME_SIZE * 2);
                raf.writeInt(id);
                readFile();
            } else if (selection == 3) {
                System.out.print("Enter new value for owing: ");
                owing = input.nextDouble();
                raf.skipBytes((NAME_SIZE * 2) + ID_SIZE);
                raf.writeDouble(owing);
                readFile();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            ex.toString();
        } catch (InputMismatchException ex) {
            System.out.println("Please, enter a number (for id or owing).");
        }
    }

    /**
     * This method reads a file, the selected part of the given file.
     */
    private static void readFile() {
        RandomAccessFile raf;

        try {
            raf = new RandomAccessFile("students.dat", "r");
            for (int i = 0; i < raf.length() / RECORD_SIZE; i++) {
                readRecord(raf, i + 1);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * This method reads a specified part of the record.
     *
     * @param raf - a file to read
     * @param choice - inserted number
     * @throws IOException
     */
    private static void readRecord(RandomAccessFile raf, int choice) throws IOException {

        raf.seek((choice - 1) * RECORD_SIZE);
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < NAME_SIZE; i++) {
            name.append(raf.readChar());
        }
        int id = raf.readInt();
        double owing = raf.readDouble();

        String format = "Student: %s, id: %d owes: $%.2f\n";
        System.out.printf(format, name.toString(), id, owing);
    }

    /**
     * This method validates input that user entered after he was asked what he wants to change in the file.
     *
     * @return choice, int
     */
    public static int validateWhatToChangeSelection() {
        while (true) {
            try {
                System.out.print("Enter what to change: 1 for name; "
                        + "2 for id; 3 for owing; 0 to exit: ");

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
     * This method validates input that user entered after he was asked what which record he wants to select.
     *
     * @return choice, int
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
