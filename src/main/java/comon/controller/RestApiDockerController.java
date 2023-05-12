package comon.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comon.dto.ImageDto;

@RestController
public class RestApiDockerController {

	private final String CONST_AWS_DIR = "c:\\comon\\";
	
	
	@GetMapping("/api/docker")
	public List<String> dockerList() throws Exception {
		final String command = "docker container ls -a --format=\"{{json .}}\" ";

		List<String> result = null;
		Process process = null;
		
		try {
			process = Runtime.getRuntime().exec(command);
			result = new BufferedReader(new InputStreamReader(process.getInputStream()))
					.lines()
					.toList();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	@PostMapping("/api/docker/write")
	public List<String> dockerRun(@RequestBody ImageDto imageDto, HttpSession session) throws Exception {
		List<String> result = null;
		
		final String name = UUID.randomUUID().toString();
		final String path = CONST_AWS_DIR + name + "\\";
		
		final String commands = 
				"cmd.exe /c "  
				+ String.format("mkdir  %s", path)
//				+ String.format("&& set userId=%s", session.getAttribute(name))
				+ String.format("&& docker container run -d --name %s -p 80 %s", name, imageDto.getImageName()) 
				+ String.format("&& docker inspect --format=\"{{(index (index .NetworkSettings.Ports \\\"80/tcp\\\") 0).HostPort}}\" %s", name)
		;
		
		try {
			Process process = Runtime.getRuntime().exec(commands);
			result = new BufferedReader(new InputStreamReader(process.getInputStream()))
					.lines()
					.toList();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}	
	
	@DeleteMapping("/api/docker/{name}")
	public List<String> dockerRemove(@PathVariable("name") String name) throws Exception {
		final String commands =
				"cmd.exe /c " 
				+ String.format("rmdir /S /Q %s%s ", CONST_AWS_DIR, name)
				+ String.format("&& docker container rm -f %s", name)
		;

		List<String> result = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(commands);
			result = new BufferedReader(new InputStreamReader(process.getInputStream()))
					.lines()
					.toList();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	@GetMapping("/api/docker/{id}")
	public List<String> dockerInspect(@PathVariable("id") String id) throws Exception {
		final String command = String.format("docker container inspect %s", id);

		List<String> result = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command);
			result = new BufferedReader(new InputStreamReader(process.getInputStream()))
					.lines()
					.toList();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
}
