
import services.NotesServices;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        NotesServices notesServices = new NotesServices();

        System.out.println("Напишите, что вы хотите сделать: create - создать; update - обновить;" +
                " delete - удалить; findByString - найти по строке; findByDate - найти по дате");

        Scanner scanner = new Scanner(System.in);
        String order = scanner.nextLine().trim();

        switch (order) {
            case "create":
                notesServices.toCreateNote();
                break;
            case "update":
                notesServices.toUpdateNote();
                break;
            case "delete":
                notesServices.toDeleteNote();
                break;
            case "findByString":
                notesServices.toFindNoteBySubstring();
                break;
            case "findByDate":
                notesServices.toFindNoteByDate();
                break;
            default:
                System.out.println("Введенное слово не соответствует ни одному из вариантов");
        }
        scanner.close();


    }

}
