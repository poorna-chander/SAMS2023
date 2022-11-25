package com.sams.samsapi.persistence;

import com.sams.samsapi.model.ResearchPaper;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.model.Pcm;

public class PaperPool {
    private ArrayList<ResearchPaper> papers;
    private HashMap<Integer, ArrayList<Pcm>> paperPCMMapping;
    private HashMap<Integer, ArrayList<Integer>> pcmPaperChoices;

}
