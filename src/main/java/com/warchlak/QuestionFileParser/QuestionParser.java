package com.warchlak.QuestionFileParser;

import com.warchlak.entity.Answer;
import com.warchlak.entity.Question;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

public class QuestionParser
{
	private QuestionMetadata metadata;
	private String fileErrorResponse;
	
	public QuestionParser(QuestionMetadata metadata)
	{
		this.metadata = metadata;
	}
	
	public List<Question> getParsedQuestions() throws NoSuchFileException, NullPointerException
	{
		fileErrorResponse = "";
		File[] questionFiles = metadata.getQuestionsDirectory().listFiles();
		
		if (questionFiles == null)
		{
			throw new NullPointerException("Selected directory is invalid!");
		}
		if (questionFiles.length == 0)
		{
			throw new NoSuchFileException("Selected directory is empty!");
		}
		
		List<Question> questions = new ArrayList<>();
		
		for (File questionFile : questionFiles)
		{
			Question question = parseQuestionFromFile(questionFile);
			if (question != null)
			{
				questions.add(question);
			}
		}
		
		return questions;
	}
	
	private Question parseQuestionFromFile(File file)
	{
		String correctAnswerLine;
		String questionLine;
		String answerLine;
		List<Answer> answers = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader
				(new InputStreamReader
						(new FileInputStream(file), Charset.forName("cp1250")))
		)
		{
			correctAnswerLine = reader.readLine().trim();
			
			if (!correctAnswerLine.startsWith("X"))
			{
				throw new InvalidFileFormatException("File " + file.getName() + " has wrong format");
			}
			
			int correctQuestionNumber = correctAnswerLine.indexOf('1') - 1;
			questionLine = reader.readLine().trim();
			
			int counter = 0;
			boolean isCorrect;
			
			while ((answerLine = reader.readLine()) != null)
			{
				isCorrect = (counter == correctQuestionNumber);
				answers.add(new Answer(answerLine.trim(), isCorrect));
				counter++;
			}
		} catch (IOException e)
		{
			fileErrorResponse += "Failed to read file: " + file.getName() + "\n";
			return null;
		}
		catch(InvalidFileFormatException e)
		{
			fileErrorResponse += e.getMessage() + "\n";
			return null;
		}
		
		return new Question(questionLine, answers);
	}
	
	public String getFileErrorResponse()
	{
		return fileErrorResponse;
	}
}
