package com.example.rma20celosmanovicselma04;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TransactionListFragment extends Fragment implements ITransactionsView {
    private ITransactionsPresenter presenter;
    private TransactionsAdapter transactionsAdapter;
    private Button leftButton, rightButton, addTransactionBtn;
    private TextView monthText, amountNumber, limitNumber;
    private ListView transactionListView;
    private Spinner filterSpinner, sortSpinner;
    private FilterAdapter filterAdapter;
    private ArrayAdapter<String> sortAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.main_fragment, container, false);
        leftButton = (Button) fragmentView.findViewById(R.id.leftButton);
        rightButton = (Button) fragmentView.findViewById(R.id.rightButton);
        monthText = (TextView) fragmentView.findViewById(R.id.monthText);
        transactionListView = (ListView) fragmentView.findViewById(R.id.transactionListView);
        filterSpinner = (Spinner) fragmentView.findViewById(R.id.filterSpinner);
        sortSpinner = (Spinner) fragmentView.findViewById(R.id.sortSpinner);
        addTransactionBtn = (Button) fragmentView.findViewById(R.id.addTransactionBtn);
        amountNumber = (TextView) fragmentView.findViewById(R.id.amountNumber);
        limitNumber = (TextView) fragmentView.findViewById(R.id.limitNumber);

        leftButton.setOnClickListener(leftAction());
        rightButton.setOnClickListener(rightAction());
        filterSpinner.setOnItemSelectedListener(spinnerTypeAction());
        sortSpinner.setOnItemSelectedListener(spinnerSortAction());

        transactionsAdapter = new TransactionsAdapter(getActivity(), R.layout.transactions_list_element, new ArrayList<>());
        transactionListView.setAdapter(transactionsAdapter);
        transactionListView.setOnItemClickListener(listItemClickListener());

        filterAdapter = new FilterAdapter(getActivity(), R.layout.transaction_spinner_element, new ArrayList<>());
        filterSpinner.setAdapter(filterAdapter);

        onItemClick = (OnItemClick) getActivity();

        sortAdapter = new ArrayAdapter<>(getActivity(), R.layout.sort_spinner_element, R.id.sortType, new ArrayList<>());
        sortSpinner.setAdapter(sortAdapter);

        addTransactionBtn.setOnClickListener(addAction());

        getPresenter().refreshTransactionsByMonthAndYear();
        getPresenter().refreshFilterAndSort((String) filterSpinner.getSelectedItem(), (String) sortSpinner.getSelectedItem());
        getPresenter().start();
        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ITransactionsPresenter getPresenter() {
        if (presenter == null) {
            presenter = new TransactionsPresenter(this, getActivity());
        }
        return presenter;
    }

    @Override
    public void setTransactions(ArrayList<Transaction> transactions) {
        transactionsAdapter.setTransactions(transactions);
    }

    public void setFilterSpinner(ArrayList<String> types) {
        filterAdapter.setTransactionType(types);
    }

    public void setSortSpinner(ArrayList<String> sort) {
        sortAdapter.addAll(sort);
    }

    @Override
    public void notifyTransactionsListDataSetChanged() {
        transactionsAdapter.notifyDataSetChanged();
    }

    public View.OnClickListener leftAction() {
        return v -> {
            getPresenter().changeMonthBackward();
            getPresenter().refreshTransactionsByMonthAndYear();
            getPresenter().refreshFilterAndSort((String) filterSpinner.getSelectedItem(), (String) sortSpinner.getSelectedItem());
        };
    }

    public View.OnClickListener rightAction() {
        return v -> {
            getPresenter().changeMonthForward();
            getPresenter().refreshTransactionsByMonthAndYear();
            getPresenter().refreshFilterAndSort((String) filterSpinner.getSelectedItem(), (String) sortSpinner.getSelectedItem());
        };
    }

    public AdapterView.OnItemSelectedListener spinnerTypeAction() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().refreshFilterAndSort((String) filterSpinner.getSelectedItem(), (String) sortSpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    public AdapterView.OnItemSelectedListener spinnerSortAction() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().refreshFilterAndSort((String) filterSpinner.getSelectedItem(), (String) sortSpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    @Override
    public void refreshDate(String date) {
        monthText.setText(date);
    }

    public AdapterView.OnItemClickListener listItemClickListener() {
        return (parent, view, position, id) -> {
            Transaction transaction = transactionsAdapter.getTransaction(position);
            onItemClick.onItemClicked(transaction);
        };
    }

    @Override
    public void onResume () {
        super.onResume();
        transactionsAdapter.setTransactions(getPresenter().filterAndSort((String) filterSpinner.getSelectedItem(), (String) sortSpinner.getSelectedItem()));
        transactionsAdapter.notifyDataSetChanged();
        getPresenter().setCurrentBudget();
    }

    public View.OnClickListener addAction() {
        return v -> {
            Bundle arguments = new Bundle();
            arguments.putBoolean("addTrn", true);
            onItemClick.onButtonClicked();
        };
    }

    public void setBudgetLimit(Double amt, Double limit) {
        amountNumber.setText(amt.toString());
        limitNumber.setText(limit.toString());
    }

    @Override
    public void setBudget(Double budget) {
        amountNumber.setText(budget.toString());
    }

    private OnItemClick onItemClick;
    public interface OnItemClick {
        void onItemClicked(Transaction transaction);

        void onButtonClicked();
    }
}
