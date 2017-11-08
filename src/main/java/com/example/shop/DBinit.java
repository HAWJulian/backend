package com.example.shop;

import com.example.shop.entities.Article;
import com.example.shop.entities.SupermarketNode;
import com.example.shop.entities.Supermarket;
import com.example.shop.repositories.ArticleRepository;
import com.example.shop.repositories.NodeRepository;
import com.example.shop.repositories.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Component
public class DBinit implements ApplicationListener<ApplicationReadyEvent>
{

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    SupermarketRepository supermarketRepository;
    @Autowired
    NodeRepository nodeRepository;

    private ArrayList<String> manufacturers;
    private ArrayList<String> productnames;
    private ArrayList<Article> articles;
    private Random rdm;
    // Wird ausgeführt, wenn Anwendung bereit ist für service requests
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent applicationReadyEvent)
    {
        ArrayList<SupermarketNode> supermarketNodes = new ArrayList<>();
        //Führt db init nur aus, wenn table leer ist
        if(articleRepository.count()==0)
        {
            System.out.println("db empty");
            manufacturers = new ArrayList<String>();
            productnames = new ArrayList<String>();
            articles = new ArrayList<Article>();
            rdm = new Random();
            dummyProductnames();
            dummyManufacturers();
            dummyArticles();
            saveArticlesInDB();
        }
        if(nodeRepository.count()==0)
        {
            ArrayList<Article> noCoolingArticles = articleRepository.findArticlesByCooling(0);
            ArrayList<Article> coolingArticles = articleRepository.findArticlesByCooling(1);
            ArrayList<Article> freezingArticles = articleRepository.findArticlesByCooling(2);
            System.out.println(noCoolingArticles.size());
            System.out.println(coolingArticles.size());
            System.out.println(freezingArticles.size());
            int amtNodesToCreate = 176;
            Long[] fridgeIdsHelper = {1L,2L,3L,4L,5L,6L,10L,12L,21L,23L,32L,34L,43L,45L,54L,65L,66L,77L,88L,105L,116L,127L,138L};
            Long[] freezerIdsHelper = {11L,22L,33L,44L,49L,50L,55L,56L,60L,61L,67L,71L,72L,78L,82L,83L,89L,93L,94L,99L,100L,110L,111L,121L,122L,132L,133L,156L,157L};
            ArrayList<Long> fridgeIds = new ArrayList<>(Arrays.asList(fridgeIdsHelper));
            ArrayList<Long> freezerIds = new ArrayList<>(Arrays.asList(freezerIdsHelper));
            //ArrayList<Long> fridge = new ArrayList<>(Arrays.asList(freezerIds))
            int maxNocoolingArticles = Math.round(noCoolingArticles.size()/amtNodesToCreate);
            int maxCoolingArticles = Math.round(coolingArticles.size()/fridgeIds.size());
            int maxFreezingArticles = Math.round(freezingArticles.size()/freezerIds.size());
            for(Long i = 1L; i < amtNodesToCreate+1; i++)
            {
                int type = 0;
                ArrayList<Long> articleIdsInNode = new ArrayList<>();
                //1. Jeder Node NocoolingArticles/amtNodes Artikel hinzufügen
                for(int j = 0; j < 1+rdm.nextInt(2); j++)
                {
                    Article tempArticle = noCoolingArticles.get(rdm.nextInt(noCoolingArticles.size()));
                    articleIdsInNode.add(tempArticle.getObjectId());
                    noCoolingArticles.remove(tempArticle);
                }
                //Wenn id in fridgeIds add coolingArticles
                //Fridge
                if(fridgeIds.contains(i))
                {
                    for(int j = 0; j < 2+rdm.nextInt(maxCoolingArticles); j++)
                    {
                        Article tempArticle = coolingArticles.get(rdm.nextInt(coolingArticles.size()));
                        articleIdsInNode.add(tempArticle.getObjectId());
                        coolingArticles.remove(tempArticle);
                    }
                    type = 1;
                }

                //Wenn id in freezingIds add freezingArticles
                //Freezer
                else if(freezerIds.contains(i))
                {
                    for(int j = 0; j < 2+rdm.nextInt(maxFreezingArticles); j++)
                    {
                        Article tempArticle = freezingArticles.get(rdm.nextInt(freezingArticles.size()));
                        articleIdsInNode.add(tempArticle.getObjectId());
                        freezingArticles.remove(tempArticle);
                    }
                    type = 2;
                }
                //Wenn weder freezing noch cooling (-> nocooling)
                //Füge weitere nocooling Artikel hinzu
                else
                {
                    for(int j = 0; j < 2; j++)
                    {
                        Article tempArticle = noCoolingArticles.get(rdm.nextInt(noCoolingArticles.size()));
                        articleIdsInNode.add(tempArticle.getObjectId());
                        noCoolingArticles.remove(tempArticle);
                    }
                }
                SupermarketNode n = new SupermarketNode(articleIdsInNode, new ArrayList<Long>(), type);
                nodeRepository.save(n);
            }
            SupermarketNode startNode = new SupermarketNode(new ArrayList<Long>(), new ArrayList<Long>(), -1);
            SupermarketNode endNode = new SupermarketNode(new ArrayList<Long>(), new ArrayList<Long>(), -2);
            nodeRepository.save(endNode);
            nodeRepository.save(startNode);
            Iterable<SupermarketNode> nodesIter = nodeRepository.findAll();
            ArrayList<SupermarketNode> nodes = new ArrayList<>();
            nodesIter.forEach(nodes::add);

            //Zeile 1
            nodes.get(0).setNeighbourIds(new ArrayList<>(Arrays.asList(2L,12L)));
            nodes.get(1).setNeighbourIds(new ArrayList<>(Arrays.asList(1L,3L,13L)));
            nodes.get(2).setNeighbourIds(new ArrayList<>(Arrays.asList(2L,4L,14L)));
            nodes.get(3).setNeighbourIds(new ArrayList<>(Arrays.asList(3L,5L,15L)));
            nodes.get(4).setNeighbourIds(new ArrayList<>(Arrays.asList(4L,6L,16L)));
            nodes.get(5).setNeighbourIds(new ArrayList<>(Arrays.asList(5L,17L)));
            nodes.get(6).setNeighbourIds(new ArrayList<>(Arrays.asList(8L,18L)));
            nodes.get(7).setNeighbourIds(new ArrayList<>(Arrays.asList(7L,9L)));
            nodes.get(8).setNeighbourIds(new ArrayList<>(Arrays.asList(8L,20L)));
            nodes.get(9).setNeighbourIds(new ArrayList<>(Arrays.asList(11L,21L)));
            nodes.get(10).setNeighbourIds(new ArrayList<>(Arrays.asList(10L,22L)));
            //Zeile 2
            nodes.get(11).setNeighbourIds(new ArrayList<>(Arrays.asList(1L,13L,23L)));
            nodes.get(12).setNeighbourIds(new ArrayList<>(Arrays.asList(12L,2L)));
            nodes.get(13).setNeighbourIds(new ArrayList<>(Arrays.asList(3L,15L)));
            nodes.get(14).setNeighbourIds(new ArrayList<>(Arrays.asList(4L,14L,26L)));
            nodes.get(15).setNeighbourIds(new ArrayList<>(Arrays.asList(5L,27L)));
            nodes.get(16).setNeighbourIds(new ArrayList<>(Arrays.asList(6L,28L)));
            nodes.get(17).setNeighbourIds(new ArrayList<>(Arrays.asList(7L,29L,19L)));
            nodes.get(18).setNeighbourIds(new ArrayList<>(Arrays.asList(18L,20L)));
            nodes.get(19).setNeighbourIds(new ArrayList<>(Arrays.asList(19L,21L,9L,31L)));
            nodes.get(20).setNeighbourIds(new ArrayList<>(Arrays.asList(20L,10L,32L)));
            nodes.get(21).setNeighbourIds(new ArrayList<>(Arrays.asList(11L,33L)));
            //Zeile 3
            nodes.get(22).setNeighbourIds(new ArrayList<>(Arrays.asList(12L,24L,34L)));
            nodes.get(23).setNeighbourIds(new ArrayList<>(Arrays.asList(23L,35L)));
            nodes.get(24).setNeighbourIds(new ArrayList<>(Arrays.asList(36L,26L)));
            nodes.get(25).setNeighbourIds(new ArrayList<>(Arrays.asList(25L,15L,37L)));
            nodes.get(26).setNeighbourIds(new ArrayList<>(Arrays.asList(16L,38L)));
            nodes.get(27).setNeighbourIds(new ArrayList<>(Arrays.asList(17L,29L)));
            nodes.get(28).setNeighbourIds(new ArrayList<>(Arrays.asList(28L,18L,30L)));
            nodes.get(29).setNeighbourIds(new ArrayList<>(Arrays.asList(29L,31L)));
            nodes.get(30).setNeighbourIds(new ArrayList<>(Arrays.asList(30L,20L,42L)));
            nodes.get(31).setNeighbourIds(new ArrayList<>(Arrays.asList(21L,43L)));
            nodes.get(32).setNeighbourIds(new ArrayList<>(Arrays.asList(22L,44L)));
            //Zeile 4
            nodes.get(33).setNeighbourIds(new ArrayList<>(Arrays.asList(23L,35L,45L)));
            nodes.get(34).setNeighbourIds(new ArrayList<>(Arrays.asList(34L,24L)));
            nodes.get(35).setNeighbourIds(new ArrayList<>(Arrays.asList(25L,37L)));
            nodes.get(36).setNeighbourIds(new ArrayList<>(Arrays.asList(36L,38L,26L,48L)));
            nodes.get(37).setNeighbourIds(new ArrayList<>(Arrays.asList(37L,39L,27L,49L)));
            nodes.get(38).setNeighbourIds(new ArrayList<>(Arrays.asList(38L,40L,50L)));
            nodes.get(39).setNeighbourIds(new ArrayList<>(Arrays.asList(39L,41L,51L)));
            nodes.get(40).setNeighbourIds(new ArrayList<>(Arrays.asList(40L,42L,52L)));
            nodes.get(41).setNeighbourIds(new ArrayList<>(Arrays.asList(41L,43L,31L,53L)));
            nodes.get(42).setNeighbourIds(new ArrayList<>(Arrays.asList(42L,32L,54L)));
            nodes.get(43).setNeighbourIds(new ArrayList<>(Arrays.asList(33L,55L)));
            //Zeile 5
            nodes.get(44).setNeighbourIds(new ArrayList<>(Arrays.asList(34L,46L)));
            nodes.get(45).setNeighbourIds(new ArrayList<>(Arrays.asList(45L,47L)));
            nodes.get(46).setNeighbourIds(new ArrayList<>(Arrays.asList(46L,58L)));
            nodes.get(47).setNeighbourIds(new ArrayList<>(Arrays.asList(37L,59L)));
            nodes.get(48).setNeighbourIds(new ArrayList<>(Arrays.asList(38L,60L)));
            nodes.get(49).setNeighbourIds(new ArrayList<>(Arrays.asList(39L,61L)));
            nodes.get(50).setNeighbourIds(new ArrayList<>(Arrays.asList(40L,62L)));
            nodes.get(51).setNeighbourIds(new ArrayList<>(Arrays.asList(41L,63L)));
            nodes.get(52).setNeighbourIds(new ArrayList<>(Arrays.asList(42L,64L)));
            nodes.get(53).setNeighbourIds(new ArrayList<>(Arrays.asList(43L,55L)));
            nodes.get(54).setNeighbourIds(new ArrayList<>(Arrays.asList(54L,44L)));
            //Zeile 6
            nodes.get(55).setNeighbourIds(new ArrayList<>(Arrays.asList(67L,57L)));
            nodes.get(56).setNeighbourIds(new ArrayList<>(Arrays.asList(56L,58L)));
            nodes.get(57).setNeighbourIds(new ArrayList<>(Arrays.asList(57L,47L,69L)));
            nodes.get(58).setNeighbourIds(new ArrayList<>(Arrays.asList(48L,70L)));
            nodes.get(59).setNeighbourIds(new ArrayList<>(Arrays.asList(49L,71L)));
            nodes.get(60).setNeighbourIds(new ArrayList<>(Arrays.asList(50L,72L)));
            nodes.get(61).setNeighbourIds(new ArrayList<>(Arrays.asList(51L,73L,63L)));
            nodes.get(62).setNeighbourIds(new ArrayList<>(Arrays.asList(62L,64L,52L)));
            nodes.get(63).setNeighbourIds(new ArrayList<>(Arrays.asList(63L,65L,53L)));
            nodes.get(64).setNeighbourIds(new ArrayList<>(Arrays.asList(64L,66L)));
            nodes.get(65).setNeighbourIds(new ArrayList<>(Arrays.asList(65L,77L)));
            //Zeile 7
            nodes.get(66).setNeighbourIds(new ArrayList<>(Arrays.asList(56L,68L,78L)));
            nodes.get(67).setNeighbourIds(new ArrayList<>(Arrays.asList(67L,69L)));
            nodes.get(68).setNeighbourIds(new ArrayList<>(Arrays.asList(68L,70L,58L)));
            nodes.get(69).setNeighbourIds(new ArrayList<>(Arrays.asList(69L,59L,81L)));
            nodes.get(70).setNeighbourIds(new ArrayList<>(Arrays.asList(60L,82L,72L)));
            nodes.get(71).setNeighbourIds(new ArrayList<>(Arrays.asList(71L,61L,83L)));
            nodes.get(72).setNeighbourIds(new ArrayList<>(Arrays.asList(62L,84L,74L)));
            nodes.get(73).setNeighbourIds(new ArrayList<>(Arrays.asList(73L,85L)));
            nodes.get(74).setNeighbourIds(new ArrayList<>(Arrays.asList(86L,76L)));
            nodes.get(75).setNeighbourIds(new ArrayList<>(Arrays.asList(75L,77L,87L)));
            nodes.get(76).setNeighbourIds(new ArrayList<>(Arrays.asList(76L,66L,88L)));
            //Zeile 8
            nodes.get(77).setNeighbourIds(new ArrayList<>(Arrays.asList(67L,89L)));
            nodes.get(78).setNeighbourIds(new ArrayList<>(Arrays.asList(80L,90L)));
            nodes.get(79).setNeighbourIds(new ArrayList<>(Arrays.asList(79L,91L)));
            nodes.get(80).setNeighbourIds(new ArrayList<>(Arrays.asList(70L,92L)));
            nodes.get(81).setNeighbourIds(new ArrayList<>(Arrays.asList(71L,93L)));
            nodes.get(82).setNeighbourIds(new ArrayList<>(Arrays.asList(72L,94L)));
            nodes.get(83).setNeighbourIds(new ArrayList<>(Arrays.asList(73L,95L)));
            nodes.get(84).setNeighbourIds(new ArrayList<>(Arrays.asList(74L,96L)));
            nodes.get(85).setNeighbourIds(new ArrayList<>(Arrays.asList(75L,97L)));
            nodes.get(86).setNeighbourIds(new ArrayList<>(Arrays.asList(76L,98L)));
            nodes.get(87).setNeighbourIds(new ArrayList<>(Arrays.asList(77L,99L)));
            //Zeile 9
            nodes.get(88).setNeighbourIds(new ArrayList<>(Arrays.asList(78L,100L)));
            nodes.get(89).setNeighbourIds(new ArrayList<>(Arrays.asList(79L,101L)));
            nodes.get(90).setNeighbourIds(new ArrayList<>(Arrays.asList(80L,92L,102L)));
            nodes.get(91).setNeighbourIds(new ArrayList<>(Arrays.asList(91L,81L,103L)));
            nodes.get(92).setNeighbourIds(new ArrayList<>(Arrays.asList(82L,104L)));
            nodes.get(93).setNeighbourIds(new ArrayList<>(Arrays.asList(83L,105L)));
            nodes.get(94).setNeighbourIds(new ArrayList<>(Arrays.asList(84L,106L)));
            nodes.get(95).setNeighbourIds(new ArrayList<>(Arrays.asList(85L,107L,97L)));
            nodes.get(96).setNeighbourIds(new ArrayList<>(Arrays.asList(96L,86L,98L)));
            nodes.get(97).setNeighbourIds(new ArrayList<>(Arrays.asList(97L,87L,99L)));
            nodes.get(98).setNeighbourIds(new ArrayList<>(Arrays.asList(98L,88L,110L)));
            //Zeile 10
            nodes.get(99).setNeighbourIds(new ArrayList<>(Arrays.asList(89L,101L)));
            nodes.get(100).setNeighbourIds(new ArrayList<>(Arrays.asList(100L,90L,102L)));
            nodes.get(101).setNeighbourIds(new ArrayList<>(Arrays.asList(101L, 91L, 113L)));
            nodes.get(102).setNeighbourIds(new ArrayList<>(Arrays.asList(92L,114L,104L)));
            nodes.get(103).setNeighbourIds(new ArrayList<>(Arrays.asList(103L,105L,93L)));
            nodes.get(104).setNeighbourIds(new ArrayList<>(Arrays.asList(104L,106L,94L,116L )));
            nodes.get(105).setNeighbourIds(new ArrayList<>(Arrays.asList(105L,107L,95L,117L)));
            nodes.get(106).setNeighbourIds(new ArrayList<>(Arrays.asList(106L,108L,96L)));
            nodes.get(107).setNeighbourIds(new ArrayList<>(Arrays.asList(107L,109L)));
            nodes.get(108).setNeighbourIds(new ArrayList<>(Arrays.asList(108L,120L)));
            nodes.get(109).setNeighbourIds(new ArrayList<>(Arrays.asList(99L,121L)));
            //Zeile 11
            nodes.get(110).setNeighbourIds(new ArrayList<>(Arrays.asList(122L,112L)));
            nodes.get(111).setNeighbourIds(new ArrayList<>(Arrays.asList(111L,113L)));
            nodes.get(112).setNeighbourIds(new ArrayList<>(Arrays.asList(112L,114L,102L)));
            nodes.get(113).setNeighbourIds(new ArrayList<>(Arrays.asList(113L,115L,103L,125L)));
            nodes.get(114).setNeighbourIds(new ArrayList<>(Arrays.asList(114L,116L)));
            nodes.get(115).setNeighbourIds(new ArrayList<>(Arrays.asList(115L,105L,127L)));
            nodes.get(116).setNeighbourIds(new ArrayList<>(Arrays.asList(106L,128L)));
            nodes.get(117).setNeighbourIds(new ArrayList<>(Arrays.asList(119L,129L)));
            nodes.get(118).setNeighbourIds(new ArrayList<>(Arrays.asList(118L,120L)));
            nodes.get(119).setNeighbourIds(new ArrayList<>(Arrays.asList(119L,109L,131L)));
            nodes.get(120).setNeighbourIds(new ArrayList<>(Arrays.asList(110L,132L)));
            //Zeile 12
            nodes.get(121).setNeighbourIds(new ArrayList<>(Arrays.asList(111L,123L,133L)));
            nodes.get(122).setNeighbourIds(new ArrayList<>(Arrays.asList(122L,124L,134L)));
            nodes.get(123).setNeighbourIds(new ArrayList<>(Arrays.asList(123L,125L,135L)));
            nodes.get(124).setNeighbourIds(new ArrayList<>(Arrays.asList(124L,114L,126L,136L)));
            nodes.get(125).setNeighbourIds(new ArrayList<>(Arrays.asList(125L,127L)));
            nodes.get(126).setNeighbourIds(new ArrayList<>(Arrays.asList(126L,116L,138L)));
            nodes.get(127).setNeighbourIds(new ArrayList<>(Arrays.asList(117L,139L)));
            nodes.get(128).setNeighbourIds(new ArrayList<>(Arrays.asList(118L,130L,140L)));
            nodes.get(129).setNeighbourIds(new ArrayList<>(Arrays.asList(129L,131L)));
            nodes.get(130).setNeighbourIds(new ArrayList<>(Arrays.asList(130L,120L,142L)));
            nodes.get(131).setNeighbourIds(new ArrayList<>(Arrays.asList(121L,143L)));
            //Zeile 13
            nodes.get(132).setNeighbourIds(new ArrayList<>(Arrays.asList(122L,134L,144L)));
            nodes.get(133).setNeighbourIds(new ArrayList<>(Arrays.asList(133L,123L,145L)));
            nodes.get(134).setNeighbourIds(new ArrayList<>(Arrays.asList(124L,146L)));
            nodes.get(135).setNeighbourIds(new ArrayList<>(Arrays.asList(125L,137L)));
            nodes.get(136).setNeighbourIds(new ArrayList<>(Arrays.asList(136L,138L,148L)));
            nodes.get(137).setNeighbourIds(new ArrayList<>(Arrays.asList(137L,127L,149L)));
            nodes.get(138).setNeighbourIds(new ArrayList<>(Arrays.asList(128L,140L,150L)));
            nodes.get(139).setNeighbourIds(new ArrayList<>(Arrays.asList(139L,129L,141L)));
            nodes.get(140).setNeighbourIds(new ArrayList<>(Arrays.asList(140L,152L)));
            nodes.get(141).setNeighbourIds(new ArrayList<>(Arrays.asList(131L,143L,153L)));
            nodes.get(142).setNeighbourIds(new ArrayList<>(Arrays.asList(142L,132L,154L)));
            //Zeile 14
            nodes.get(143).setNeighbourIds(new ArrayList<>(Arrays.asList(133L,155L)));
            nodes.get(144).setNeighbourIds(new ArrayList<>(Arrays.asList(134L,156L)));
            nodes.get(145).setNeighbourIds(new ArrayList<>(Arrays.asList(135L,147L,157L)));
            nodes.get(146).setNeighbourIds(new ArrayList<>(Arrays.asList(146L,148L,158L)));
            nodes.get(147).setNeighbourIds(new ArrayList<>(Arrays.asList(147L,137L,159L)));
            nodes.get(148).setNeighbourIds(new ArrayList<>(Arrays.asList(138L,160L)));
            nodes.get(149).setNeighbourIds(new ArrayList<>(Arrays.asList(139L,151L)));
            nodes.get(150).setNeighbourIds(new ArrayList<>(Arrays.asList(150L,152L)));
            nodes.get(151).setNeighbourIds(new ArrayList<>(Arrays.asList(151L,153L,163L,141L)));
            nodes.get(152).setNeighbourIds(new ArrayList<>(Arrays.asList(152L,142L)));
            nodes.get(153).setNeighbourIds(new ArrayList<>(Arrays.asList(143L,165L)));
            //Zeile 15
            nodes.get(154).setNeighbourIds(new ArrayList<>(Arrays.asList(144L,156L,166L)));
            nodes.get(155).setNeighbourIds(new ArrayList<>(Arrays.asList(155L,157L,145L,167L)));
            nodes.get(156).setNeighbourIds(new ArrayList<>(Arrays.asList(156L,146L,168L)));
            nodes.get(157).setNeighbourIds(new ArrayList<>(Arrays.asList(147L,159L,169L)));
            nodes.get(158).setNeighbourIds(new ArrayList<>(Arrays.asList(158L,148L,170L)));
            nodes.get(159).setNeighbourIds(new ArrayList<>(Arrays.asList(149L,171L)));
            nodes.get(160).setNeighbourIds(new ArrayList<>(Arrays.asList(162L,172L)));
            nodes.get(161).setNeighbourIds(new ArrayList<>(Arrays.asList(161L,163L)));
            nodes.get(162).setNeighbourIds(new ArrayList<>(Arrays.asList(162L,152L,174L)));
            nodes.get(163).setNeighbourIds(new ArrayList<>(Arrays.asList(165L,175L)));
            nodes.get(164).setNeighbourIds(new ArrayList<>(Arrays.asList(164L,154L,176L)));
            //Zeile 16
            //Rechts: get+2
            //Unten: get+12
            //Links: get
            //Oben: get-10
            //Endnode in
            nodes.get(165).setNeighbourIds(new ArrayList<>(Arrays.asList(155L,167L,177L)));
            nodes.get(166).setNeighbourIds(new ArrayList<>(Arrays.asList(166L,156L,168L)));
            nodes.get(167).setNeighbourIds(new ArrayList<>(Arrays.asList(167L,169L,157L)));
            nodes.get(168).setNeighbourIds(new ArrayList<>(Arrays.asList(168L,158L)));
            nodes.get(169).setNeighbourIds(new ArrayList<>(Arrays.asList(159L,171L)));
            nodes.get(170).setNeighbourIds(new ArrayList<>(Arrays.asList(170L,160L)));
            nodes.get(171).setNeighbourIds(new ArrayList<>(Arrays.asList(161L,173L)));
            nodes.get(172).setNeighbourIds(new ArrayList<>(Arrays.asList(172L,174L)));
            nodes.get(173).setNeighbourIds(new ArrayList<>(Arrays.asList(173L,163L,175L)));
            nodes.get(174).setNeighbourIds(new ArrayList<>(Arrays.asList(174L,164L,176L)));
            //Startnode in
            nodes.get(175).setNeighbourIds(new ArrayList<>(Arrays.asList(175L,165L,178L)));
            nodes.get(176).setNeighbourIds(new ArrayList<>(Arrays.asList(166L)));
            nodes.get(177).setNeighbourIds(new ArrayList<>(Arrays.asList(176L)));
            nodeRepository.save(nodes);
            //SupermarketNode neighbourManipulationNode = nodeRepository.findOne(1L).setNeighbourIds(new ArrayList<Long>(Arrays.asList(2L,12L)));
            //nodeRepository.save(nodeRepository.findOne(1L).setNeighbourIds(new ArrayList<Long>(Arrays.asList(2L,12L))));
            /*
            Iterable<Article> arts = articleRepository.findAll();
            ArrayList<Article> availableArticles = new ArrayList<Article>();
            arts.forEach(availableArticles::add);
            //Nodes erstellen
            for(int i = 0; i < 35; i++)
            {
                //Anzahl Artikel die einer SupermarketNode hinzugefügt werden
                int amtArticlesInNode = 1+rdm.nextInt(4);
                //Artikel Ids, die der SupermarketNode hinzugefügt werden
                ArrayList<Long> articlesInNode = new ArrayList<Long>();
                for(int j = 0; j < amtArticlesInNode; j++)
                {
                    Article rdmArticle = availableArticles.get(rdm.nextInt(availableArticles.size()));
                    articlesInNode.add(rdmArticle.getObjectId());
                    availableArticles.remove(rdmArticle);
                }
                long[] nei = {1L,2L,3L,4L};
                int[][] tiles = {};
                SupermarketNode n = new SupermarketNode(articlesInNode, nei, tiles );
                supermarketNodes.add(n);
            }
            nodeRepository.save(supermarketNodes);
            */
        }
        if(supermarketRepository.count()==0)
        {
            ArrayList<Supermarket> s = new ArrayList<Supermarket>();
            ArrayList<Long> nodeids = new ArrayList<>();
            for (long i = 1; i < 179; i++)
            {
                nodeids.add(i);
            }
            s.add(new Supermarket(nodeids));
            supermarketRepository.save(s);
        }
        return;
    }
    private void saveArticlesInDB()
    {
        articleRepository.save(articles);
        System.out.println("repo count post"+articleRepository.count());
    }
    private void dummyProductnames()
    {
        productnames.add("Käse");
        productnames.add("Salami");
        productnames.add("Salat");
        productnames.add("Milch");
        productnames.add("Brot");
        productnames.add("Pizza");
        productnames.add("Mate");
        productnames.add("Paprika");
        productnames.add("Tomaten");
        productnames.add("Nudeln");
        productnames.add("Joghurt");
        productnames.add("Zwiebeln");
        productnames.add("Gurken");
        productnames.add("Kuchen");
        productnames.add("Eis");
        productnames.add("Brötchen");
        productnames.add("Gummibärchen");
        productnames.add("Lachs");
        productnames.add("Müsli");
        productnames.add("Kekse");
        productnames.add("Orangen");
        productnames.add("Feldsalat");
        productnames.add("Äpfel");
        productnames.add("Zucchini");
        productnames.add("Bier");
        productnames.add("Fenchel");
        productnames.add("Basilikum");
        productnames.add("Kaugummie");
        productnames.add("Pudding");
        productnames.add("Ahornsirup");
        productnames.add("Klopapier");
        productnames.add("Champignons");
        productnames.add("Karotten");
        productnames.add("Ketchup");
        productnames.add("Senf");
        productnames.add("Kaffee");
        productnames.add("Pferferminztee");
        productnames.add("Mehl");
        productnames.add("Eier");
        productnames.add("Honig");
        productnames.add("Rotwein");
        productnames.add("Studentenfutter");
        productnames.add("Frühlingsrolle");
        productnames.add("Mini-Berliner");
        productnames.add("Müsliriegel");
        productnames.add("Salzstangen");
        productnames.add("Cola");
        productnames.add("Toastbrot");
        productnames.add("Blätterteig");
        productnames.add("Hackfleisch");
        productnames.add("Gemüsedelle");
        productnames.add("Räuchertofu");
        productnames.add("Magerquark");
        productnames.add("Hokkaidokürbis");


    }

    private void dummyManufacturers()
    {
        manufacturers.add("Ja!");
        manufacturers.add("Nein!");
        manufacturers.add("Milka");
        manufacturers.add("Alpro");
        manufacturers.add("Milupa");
        manufacturers.add("Sanella");
        manufacturers.add("Seitenbacher");
        manufacturers.add("Rügenwalder");
        manufacturers.add("Gutfried");
        manufacturers.add("Rewe Bio");
        manufacturers.add("Fritz");
        manufacturers.add("Müller");
        manufacturers.add("Leibniz");
        manufacturers.add("kitekat");
        manufacturers.add("Bofrost");
        manufacturers.add("Barilla");
        manufacturers.add("Haribo");
        manufacturers.add("Katjes");
        manufacturers.add("Tchibo");
        manufacturers.add("Teekanne");

    }
    private void dummyArticles()
    {
        int amtNocooling = 0;
        int amtCooling = 0;
        int amtFreezing = 0;
        System.out.println("dummyarticles");
        for (String manufac : manufacturers)
        {
            for (String product : productnames)
            {
                int rdmNr = rdm.nextInt(20);
                //Artikel ungekühlt
                if(rdmNr < 14)
                {
                    articles.add(new Article(product, 10 + rdm.nextInt(290), manufac, 0));
                    amtNocooling++;
                }
                //Artikel gekühlt
                else if(rdmNr < 17)
                {
                    articles.add(new Article(product, 10 + rdm.nextInt(290), manufac, 1));
                    amtCooling++;
                }
                //Artikel tiefgefroren
                else
                {
                    articles.add(new Article(product, 10 + rdm.nextInt(290), manufac, 2));
                    amtFreezing++;
                }
            }
        }
        System.out.println(articles.size());
        System.out.println("Article prints");
        System.out.println(amtNocooling);
        System.out.println(amtCooling);
        System.out.println(amtFreezing);
    }
}
