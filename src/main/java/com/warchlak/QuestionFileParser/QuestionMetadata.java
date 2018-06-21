package com.warchlak.QuestionFileParser;


import java.io.File;
import java.nio.file.NotDirectoryException;

public class QuestionMetadata
{
	private File questionsDirectory;
	private String courseName;
	
	public QuestionMetadata(String pathLocation, String courseName) throws NotDirectoryException
	{
		
		if (pathLocation == null)
		{
			throw new NullPointerException("No directory selected");
		}
		
		questionsDirectory = new File(pathLocation);
		
		if (!questionsDirectory.isDirectory())
		{
			throw new NotDirectoryException("Selected question base: " + pathLocation + " is not a directory");
		}
		
		this.courseName = courseName;
	}
	
	public File getQuestionsDirectory()
	{
		return questionsDirectory;
	}
	
	public String getCourseName()
	{
		return courseName;
	}
}
