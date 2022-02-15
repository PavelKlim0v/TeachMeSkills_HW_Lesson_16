package com.teachmeskills.lesson_16.task_1.parser;

import com.teachmeskills.lesson_16.task_1.document.Document;
import com.teachmeskills.lesson_16.task_1.exception.FolderIsEmptyException;
import com.teachmeskills.lesson_16.task_1.exception.IncorrectInputException;
import com.teachmeskills.lesson_16.task_1.exception.InvalidPathException;
import com.teachmeskills.lesson_16.task_1.exception.NoMatchingFilesException;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parser implements IParser{

    private Map<String, Document> mapList = new HashMap<>();
    public static int countProcessedDoc;    // счетчик успешно прочитанных файлов
    public static int countInvalid;         // счетчик не прочитанных файлов

    @Override
    public Map<String, Document> parse(String pathToFolder, int countToParse)
            throws FolderIsEmptyException,IncorrectInputException,NoMatchingFilesException,InvalidPathException {

        File folder = new File(pathToFolder);

        if(folder.isDirectory()){

            // проверяем, пустая ли папка
            if(folder.length() == 0) {
                throw new FolderIsEmptyException("Эта папка пуста.");
            }

            // проверка ввода кол-ва файлов
            if(countToParse <= 0) {
                throw new IncorrectInputException("Неверное кол-во, проверяемых текстовых документов.");
            }

            // если в папке меньше файлов, чем задано, то обрабатываем все файлы
            File[] arrFiles = folder.listFiles();
            if (arrFiles.length < countToParse) {
                countToParse = arrFiles.length;
            }

            // получаем только ТХТ файл согласно условию
            List<File> files = Arrays.stream(folder.listFiles( (dir, name) -> name.endsWith("txt") ))
                    .limit(countToParse)
                    .collect(Collectors.toList());

            // проверка на то, что после фильтрации есть файлы, подходящие под наше условие
            if(files.size() == 0){
                throw new NoMatchingFilesException("Нет подходящих файлов формата txt.");
            }

            System.out.println("Путь к файлам: "+ files);

            String nameFile;
            int n;

            for(File file: files){
                n = file.getName().indexOf(".txt");     //n = file.getName().replaceAll("\\.\\w+", "");  // (не получилось)
                nameFile = file.getName().substring(0, n);

                mapList.put(nameFile, readFile(file));
            }

        } else {
            throw new InvalidPathException("Невалидный путь.");
        }

        return mapList;
    }


    private Document readFile(File file) {
        Pattern docPattern = Pattern.compile("\\d{4}[-][a-zа-я]{3}[-]\\d{4}[-][a-zа-я]{3}[-]\\d[a-zа-я]\\d[a-zа-я]", Pattern.CASE_INSENSITIVE);
        Pattern phonePattern = Pattern.compile("(\\+*)[(]\\d{2}[)]\\d{7}([\\W\\n\\t]|$)", Pattern.CASE_INSENSITIVE);
        Pattern emailPattern = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}", Pattern.CASE_INSENSITIVE);

        Document document = new Document();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String docOneLine;

            // читаем документ посстрочно и анализируем
            while((docOneLine = reader.readLine()) != null) {
                Matcher docMatcher = docPattern.matcher(docOneLine);
                Matcher phoneMatcher = phonePattern.matcher(docOneLine);
                Matcher emailMatcher = emailPattern.matcher(docOneLine);

                while (docMatcher.find()) {
                    document.addDocPattern(docOneLine.substring(docMatcher.start(), docMatcher.end()));
                }
                while (phoneMatcher.find()) {
                    document.addPhonePattern(docOneLine.substring(phoneMatcher.start(), phoneMatcher.end()));
                }
                while (emailMatcher.find()) {
                    document.addEmailPattern(docOneLine.substring(emailMatcher.start(), emailMatcher.end()));
                }
            }
            countProcessedDoc++;    // счетчик успешно прочитанных файлов

        } catch (IOException e) {
            countInvalid++;         // счетчик не прочитанных файлов
            System.out.println(e.getMessage());
        }

        return document;
    }

}

