package com.teachmeskills.lesson_16.task_1.runner;

import com.teachmeskills.lesson_16.task_1.document.Document;
import com.teachmeskills.lesson_16.task_1.exception.FolderIsEmptyException;
import com.teachmeskills.lesson_16.task_1.exception.IncorrectInputException;
import com.teachmeskills.lesson_16.task_1.exception.InvalidPathException;
import com.teachmeskills.lesson_16.task_1.exception.NoMatchingFilesException;
import com.teachmeskills.lesson_16.task_1.parser.IParser;
import com.teachmeskills.lesson_16.task_1.parser.Parser;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *  Задание 1. (Основное задание)
 *    Написать пограмму со следующим функционалом:
 *
 *    Программа на вход получает путь к папке (задается через консоль).
 *    В заданной папке находятся текстовые файлы (т.е. текстовые документы, формат тхт).
 *    Каждый файл содержит произвольный текст. В этом тексте может быть номер документа(один или несколько), емейл и номер телефона.
 * 	   номер документа в формате: xxxx-yyy-xxxx-yyy-xyxy, где x - это любая цифра, а y - это любая буква русского или латинского алфавита
 * 	   номер телефона в формате: +(ХХ)ХХХХХХХ
 *
 *    Документ может содержать не всю информацию, т.е. например, может не содержать номер телефона, или другое поле.
 *    Необходимо извлечь информацию из N текстовых документов. Число документов для обработки N задается с консоли.
 *    Если в папке содержится меньше документов, чем заданое число - следует обрабатывать все документы.
 *    Извлеченную информацию необходимо сохранить в следующую стурктуру данных:
 *    Map<String, Document>, где
 * 	   ключ типа String - это имя документа без расширения,
 * 	   значение типа Document - объект кастомного класса, поля которого содержат извлеченную из текстового документа информацию
 *
 *    Учесть вывод сообщений на случаи если,
 * 	   - на вход передан путь к папке, в которой нет файлов
 * 	   - все файлы имеют неподходящий формат (следует обрабатывать только тхт файлы)
 * 	   - так же сообщения на случай других исключительных ситуаций
 *
 *    В конце работы программы следует вывести сообщение о том, сколько документов обработано и сколько было документов
 *     невалидного формата.
 */

public class MainTask_1 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Укажите путь к папке: ");     // src/com/teachmeskills/lesson_16/task_1/folder
        String enterStr = scan.nextLine();
        System.out.print("Укажите кол-во текстовых документов: ");
        int enterNum = scan.nextInt();

        IParser parser = new Parser();
        Map<String,Document> map = new HashMap<>();

        try {
            map = parser.parse(enterStr, enterNum);

        } catch (NoMatchingFilesException | FolderIsEmptyException | IncorrectInputException | InvalidPathException e) {
            System.out.println("Exception: "+ e.getMessage() +" In class: "+ e.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("Exception: "+ e.getMessage() +" In class: "+ e.getClass().getSimpleName());
        }

        if (map != null) {
            System.out.println(map);
        }

        System.out.println("Документов обработано: "+ Parser.countProcessedDoc);
        if (Parser.countInvalid != 0) {
            System.out.println("Документов невалидного формата: " + Parser.countInvalid);
        }

        scan.close();
    }

}

