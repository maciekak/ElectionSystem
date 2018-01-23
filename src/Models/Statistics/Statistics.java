package Models.Statistics;

import Models.Election.Candidate;
import Models.Election.Election;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Statistics
{
    private Election election;

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




}
