package com.teachmeskills.lesson_16.task_1.parser;

import com.teachmeskills.lesson_16.task_1.document.Document;
import com.teachmeskills.lesson_16.task_1.exception.FolderIsEmptyException;
import com.teachmeskills.lesson_16.task_1.exception.IncorrectInputException;
import com.teachmeskills.lesson_16.task_1.exception.InvalidPathException;
import com.teachmeskills.lesson_16.task_1.exception.NoMatchingFilesException;
import java.util.Map;

public interface IParser {

    Map<String, Document> parse(String pathToFolder, int countToParse)
            throws FolderIsEmptyException, IncorrectInputException, NoMatchingFilesException, InvalidPathException;

}