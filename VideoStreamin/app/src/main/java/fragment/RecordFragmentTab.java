package fragment;

/**
 * Created by root on 9/14/15.
 */

 import android.os.Bundle;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.AdapterView;
 import android.widget.ListView;
 import android.widget.TextView;

 import org.json.JSONArray;

 import mshopmobile.app.com.videostreaming.R;
 import mshopmobile.app.com.videostreaming.VideoTabManagerActivity;
 import util.ServiceIdentification;

public class RecordFragmentTab extends android.support.v4.app.Fragment {
    View view;
    ListView list;
    RecordAdapterList adapter;
    TextView no_items_tv;
    JSONArray RecordListToDisplay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_record_list_tab, container, false);

        view = rootView;

        /*
        if (savedInstanceState != null) {
            // Restore value of members from saved state

        } else {
            // Probably initialize members with default values for a new instance
            performRecordList();
        }
        */
        performRecordList();
        return rootView;

    }
    /*
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }
    */
    //perform the record list
    public void performRecordList(){

        RecordListToDisplay = new JSONArray();
        list=(ListView)view.findViewById(R.id.listRecord);
        adapter=new RecordAdapterList(getActivity(),RecordListToDisplay);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.i("clickON",""+position);
                ((VideoTabManagerActivity)getActivity()).viewRecordDetail(position);

            }
        });
        ((VideoTabManagerActivity)getActivity()).callListElements(adapter,list,no_items_tv,ServiceIdentification.recordList,view,null);
    }

}

