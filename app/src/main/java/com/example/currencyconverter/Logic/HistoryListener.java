package com.example.currencyconverter.Logic;

import java.util.List;
import java.util.Stack;

public interface HistoryListener {
    void onChange(Stack<String> history);
}
