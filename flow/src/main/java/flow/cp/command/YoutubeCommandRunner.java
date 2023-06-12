package flow.cp.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import flow.cp.service.YoutubeService;

@Component
public class YoutubeCommandRunner implements CommandLineRunner {
    
    @Autowired
    private YoutubeService youtubeService;

    @Override
    public void run(String... args) throws Exception {
        youtubeService.main(args);
    }
}