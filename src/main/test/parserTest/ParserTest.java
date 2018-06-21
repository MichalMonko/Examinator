package parserTest;


import com.warchlak.QuestionFileParser.QuestionMetadata;
import com.warchlak.QuestionFileParser.QuestionParser;
import com.warchlak.entity.Question;
import org.junit.jupiter.api.*;

import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.util.List;

public class ParserTest
{
	private QuestionMetadata metadata;
	private QuestionParser parser;
	
	@BeforeEach
	public void initializeMetadata()
	{
		try
		{
			metadata = new QuestionMetadata(
					"src\\main\\resources\\TestResources\\ParserTest\\baza", "Sample name");
			parser = new QuestionParser(metadata);
		} catch (Exception e)
		{
		
		}
	}
	
	@Test
	public void testNullPointerExceptionThrownWhenConstructingMetadataWithNull()
	{
		Assertions.assertThrows(NullPointerException.class, () ->
		{
			QuestionMetadata metadata = new QuestionMetadata(null, "SampleName");
		});
		Assertions.assertThrows(NullPointerException.class, () ->
		{
			QuestionMetadata metadata = new QuestionMetadata(null, null);
		});
	}
	
	@Test
	public void assertNotDirectoryExceptionWhenInvalidPathProvided()
	{
		Assertions.assertThrows(NotDirectoryException.class, () ->
		{
			QuestionMetadata metadata = new QuestionMetadata("samplePath", "SampleName");
		});
	}
	
	@Test
	public void assertExceptionWhenEmptyDirectoryPassedToGetParsedQuestions()
	{
		Assertions.assertThrows(NoSuchFileException.class, () ->
		{
			metadata = new QuestionMetadata(
					"src\\main\\resources\\TestResources\\ParserTest\\baza", "Sample name");
			QuestionParser parser = new QuestionParser(metadata);
			parser.getParsedQuestions();
		});
	}
	
	@Test
	public void assertAllQuestionPresentWhenParsingProperData()
	{
		try
		{
			List<Question> questions = parser.getParsedQuestions();
			Assertions.assertEquals(questions.size(),
					metadata.getQuestionsDirectory().listFiles().length);
		} catch (Exception e)
		{
		
		}
	}
	
	@Test
	public void assertQuestionParsedProperly()
	{
		try
		{
			List<Question> questions = parser.getParsedQuestions();
			Question firstQuestion = questions.get(0);
			
			Assertions.assertEquals(firstQuestion.getContent(),
					"Półprzewodnik samoistny to: ");
			Assertions.assertEquals(firstQuestion.getCorrectAnswer().getContent(),
					"c) czysty półprzewodnik pozbawiony domieszek i z efektów sieci ");
			
			Assertions.assertEquals(firstQuestion.getAnswers().get(0),
					"a) materiał samoistnie generujący prąd ");
			
			Assertions.assertEquals(firstQuestion.getAnswers().get(1),
					"b) półprzewodnik z pojedynczym elektronem walencyjnym ");
			
			Assertions.assertEquals(firstQuestion.getAnswers().get(2),
					"c) czysty półprzewodnik pozbawiony domieszek i z efektów sieci");
			
			Assertions.assertEquals(firstQuestion.getAnswers().get(3),
					"d) materiał który samoistnie powstał w wyniku powstałego mieszku ");
			
		} catch (Exception e)
		{
		
		}
	}
}
