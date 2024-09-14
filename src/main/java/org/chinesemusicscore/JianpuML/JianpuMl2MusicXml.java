package org.chinesemusicscore.JianpuML;

import org.audiveris.proxymusic.ScorePartwise;
import org.audiveris.proxymusic.util.Marshalling;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class JianpuMl2MusicXml {

	public static void main(String[] args) throws Exception {
		File inputfile = new File("input.jml");
		String jianpu = new String(Files.readAllBytes(inputfile.toPath()));

		JianpuMLParser jianpuMLParser = new JianpuMLParser();
		ScorePartwise scorePartwise = jianpuMLParser.parseJianpuML(jianpu);

		String workName = scorePartwise.getIdentification().getCreator().getFirst().getValue()
				+ "-" + scorePartwise.getWork().getWorkTitle();
		File outputDir = new File("output", workName);
		if(outputDir.exists()){
			outputDir.delete();
		}
		outputDir.mkdirs();

		File outputFile = new File("output.musicxml");
		OutputStream musicxmlOs = new FileOutputStream(outputFile);
		Marshalling.marshal(scorePartwise, musicxmlOs, false, 4);

		File jmlFile = new File(outputDir, workName+".jml");
		FileCopyUtils.copy(inputfile, jmlFile);
		File musicxmlFile = new File(outputDir ,workName+".musicxml");
		FileCopyUtils.copy(outputFile, musicxmlFile);

		System.out.println("MusicXML file has been created in " + outputDir.getAbsolutePath());
	}
}
