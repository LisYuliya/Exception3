import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class UserData {
    private String surname;
    private String name;
    private String patronymic;
    private long phoneNumber;

    public UserData(String surname, String name, String patronymic, long phoneNumber) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }
}

class DataFormatException extends Exception {
    public DataFormatException(String message) {
        super(message);
    }
}

class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите данные (Фамилия Имя Отчество номер телефона): ");
            String inputData = scanner.nextLine();

            try {
                UserData userData = parseUserData(inputData);
                saveUserDataToFile(userData);
                System.out.println("Данные успешно сохранены в файл.");
            } catch (DataFormatException e) {
                System.out.println("Ошибка формата данных: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Ошибка при чтении-записи файла: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static UserData parseUserData(String inputData) throws DataFormatException {
        String[] dataParts = inputData.split(" ");

        if (dataParts.length > 4) {
            throw new DataFormatException("Вы ввели больше данных,чем требуется.");
        }
        if (dataParts.length < 4) {
            throw new DataFormatException("Вы ввели меньше данных, чем требуется.");
        }

        String surname = dataParts[0];
        String name = dataParts[1];
        String patronymic = dataParts[2];
        long phoneNumber;

        try {
            phoneNumber = Long.parseLong(dataParts[3]);
        } catch (NumberFormatException e) {
            throw new DataFormatException("Неверный формат номера телефона.");
        }

        return new UserData(surname, name, patronymic, phoneNumber);
    }

    private static void saveUserDataToFile(UserData userData) throws IOException {
        String filename = userData.getSurname() + ".txt";

        try (FileWriter fileWriter = new FileWriter(filename, true)) {
            String dataLine = userData.getSurname() + " " + userData.getName() + " " +
                    userData.getPatronymic() + " " + userData.getPhoneNumber();
            fileWriter.write(dataLine + System.lineSeparator());
        }
    }
}
