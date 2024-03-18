package com.example.Utils;

import java.util.Comparator;

public class LeaderboardEntryComparator implements Comparator<LeaderboardEntry> {

    @Override
    public int compare(LeaderboardEntry entry1, LeaderboardEntry entry2) {
        // highest score first
        return entry2.getScore() - entry1.getScore();
    }
}

