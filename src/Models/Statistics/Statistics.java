package Models.Statistics;

import Models.Election.Candidate;
import Models.Election.Election;
import javafx.util.Pair;

import java.util.*;

public class Statistics
{
    private Election election;
    private HashMap<String, List<Candidate>> partiesCandidates;
    private HashMap<String, Double> partiesPercents;
    private HashMap<String, Double> partiesAboveLimit;
    private HashMap<String, Double> mandatsPercent;
    private HashMap<String, List<Candidate>> mandats;

    public Statistics(Election election)
    {
        this.election = election;
    }

    public List<Candidate> getCandidatesListSorted()
    {
        election.getCandidates().sort(Comparator.comparingInt(Candidate::getVotesCount));
        Collections.reverse(election.getCandidates());
        return election.getCandidates();
    }

    public HashMap<String, List<Candidate>> getPartiesCandidates()
    {
        HashMap<String, List<Candidate>> partiesStatistics = new HashMap<>();

        for (Candidate candidate : election.getCandidates())
        {
            if(!partiesStatistics.containsKey(candidate.getPerson().getParty()))
            {
                ArrayList<Candidate> candidates = new ArrayList<>();
                candidates.add(candidate);

                partiesStatistics.put(candidate.getPerson().getParty(), candidates);
                continue;
            }

            partiesStatistics.get(candidate.getPerson().getParty()).add(candidate);
        }

        for(String party : partiesStatistics.keySet())
        {
            partiesStatistics.get(party).sort(Comparator.comparingInt(Candidate::getVotesCount));
            Collections.reverse(partiesStatistics.get(party));
        }

        this.partiesCandidates = partiesStatistics;
        return partiesStatistics;
    }

    public HashMap<String, Double> getPartiesPercent()
    {
        int allVotes = 0;
        HashMap<String, Double> partiesPercents = new HashMap<>();
        if(partiesCandidates == null)
            getPartiesCandidates();

        for (String party : partiesCandidates.keySet())
        {
            partiesPercents.put(party, 0.0);
            for(Candidate candidate : partiesCandidates.get(party))
            {
                partiesPercents.replace(party, candidate.getVotesCount() + partiesPercents.get(party));
                allVotes += candidate.getVotesCount();
            }
        }

        for(String party : partiesPercents.keySet())
        {
            partiesPercents.replace(party, partiesPercents.get(party) / allVotes * 100);
        }

        this.partiesPercents = partiesPercents;
        return partiesPercents;
    }

    public HashMap<String, Double> getPartiesAboveLimit()
    {
        Double limit = 5.0;
        HashMap<String, Double> partiesAboveLimit = new HashMap<>();

        if(partiesPercents == null)
            getPartiesPercent();

        for(String party : partiesPercents.keySet())
        {
            if(partiesPercents.get(party) >= limit)
                partiesAboveLimit.put(party, limit);
        }

        this.partiesAboveLimit = partiesAboveLimit;

        return partiesAboveLimit;
    }

    public HashMap<String, Double> getMandatsPercent()
    {
        HashMap<String, Double> mandatsPercent = new HashMap<>();
        Double sum = getPercentsSum();

        for(String party : partiesAboveLimit.keySet())
        {
            mandatsPercent.put(party, partiesAboveLimit.get(party)*100/sum);
        }

        this.mandatsPercent = mandatsPercent;

        return mandatsPercent;
    }

    public HashMap<String, List<Candidate>> getMandats(int mandatsLimit)
    {
        HashMap<String, List<Candidate>> mandats = new HashMap<>();
        if(partiesCandidates == null)
            getPartiesCandidates();

        if(mandatsPercent == null)
            getMandatsPercent();


        for(String party : mandatsPercent.keySet())
        {
            int mandatsCount = (int)(mandatsLimit*mandatsPercent.get(party));

            //TODO chekc what will happend
            mandats.put(party, new ArrayList<>(partiesCandidates.get(party).subList(0, mandatsCount)));
        }
        this.mandats = mandats;
        return mandats;
    }

    private Double getPercentsSum()
    {
        Double sum = 0.0;
        if(partiesAboveLimit == null)
            getPartiesAboveLimit();

        for(String party : partiesAboveLimit.keySet())
        {
            sum += partiesAboveLimit.get(party);
        }

        return sum;
    }
}