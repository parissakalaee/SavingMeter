package ir.Parka.keychi;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FragmentBank extends Fragment {


    public FragmentBank() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(ir.Parka.keychi.R.layout.fragment_bank, container, false);
        HelperUi.persianizer((ViewGroup) view);

        GridView gridview = (GridView) view.findViewById(ir.Parka.keychi.R.id.gridview);

        List<ItemObject> allItems = getAllItemObject();
        CustomAdapter customAdapter = new CustomAdapter(getContext(), allItems);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bankAddress[position]));
                startActivity(browserIntent);
            }
        });

        return view;
    }

    public class ItemObject {

        private String content;
        private String imageResource;

        public ItemObject(String content, String imageResource) {
            this.content = content;
            this.imageResource = imageResource;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImageResource() {
            return imageResource;
        }

        public void setImageResource(String imageResource) {
            this.imageResource = imageResource;
        }
    }

    public class CustomAdapter extends BaseAdapter {

        private LayoutInflater layoutinflater;
        private List<ItemObject> listStorage;
        private Context context;

        public CustomAdapter(Context context, List<ItemObject> customizedListView) {
            this.context = context;
            layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;
            if (convertView == null) {
                listViewHolder = new ViewHolder();
                convertView = layoutinflater.inflate(ir.Parka.keychi.R.layout.item_bank, parent, false);
                listViewHolder.textInListView = (TextView) convertView.findViewById(ir.Parka.keychi.R.id.textView);
                listViewHolder.imageInListView = (ImageView) convertView.findViewById(ir.Parka.keychi.R.id.imageView);
                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (ViewHolder) convertView.getTag();
            }

            listViewHolder.textInListView.setText(listStorage.get(position).getContent());
            int imageResourceId = this.context.getResources().getIdentifier(listStorage.get(position).getImageResource(), "mipmap", this.context.getPackageName());
            listViewHolder.imageInListView.setImageResource(imageResourceId);

            return convertView;
        }

        public class ViewHolder {
            TextView textInListView;
            ImageView imageInListView;
        }
    }

    private List<ItemObject> getAllItemObject() {
        ItemObject itemObject = null;
        List<ItemObject> items = new ArrayList<>();
        items.add(new ItemObject(" ", "tosee_saderat"));
        items.add(new ItemObject(" ", "tosee_taavon"));
        items.add(new ItemObject(" ", "post_bank"));
        items.add(new ItemObject(" ", "keshavarzi"));
        items.add(new ItemObject(" ", "sanat_o_maadan"));
        items.add(new ItemObject(" ", "sepah"));
        items.add(new ItemObject(" ", "melli"));
        items.add(new ItemObject(" ", "maskan"));
        items.add(new ItemObject(" ", "markazi"));

        items.add(new ItemObject(" ", "ansar"));
        items.add(new ItemObject(" ", "eghtesade_novin"));
        items.add(new ItemObject(" ", "ayande"));
        items.add(new ItemObject(" ", "parsian"));
        items.add(new ItemObject(" ", "iran_venezoela"));
        items.add(new ItemObject(" ", "iran_zamin"));
        items.add(new ItemObject(" ", "hekmate_iranian"));
        items.add(new ItemObject(" ", "tejarat"));
        items.add(new ItemObject(" ", "pasargad"));
        items.add(new ItemObject(" ", "refah"));
        items.add(new ItemObject(" ", "day"));
        items.add(new ItemObject(" ", "khavarmianeh"));
        items.add(new ItemObject(" ", "sina"));
        items.add(new ItemObject(" ", "sarmayeh"));
        items.add(new ItemObject(" ", "saman"));
        items.add(new ItemObject(" ", "ghavamin"));
        items.add(new ItemObject(" ", "saderat"));
        items.add(new ItemObject(" ", "shahr"));
        items.add(new ItemObject(" ", "mellat"));
        items.add(new ItemObject(" ", "gardeshgari"));
        items.add(new ItemObject(" ", "karafarin"));

        items.add(new ItemObject(" ", "kosar"));
        items.add(new ItemObject(" ", "askarieh"));
        items.add(new ItemObject(" ", "tosee"));

        items.add(new ItemObject(" ", "mehr"));
        items.add(new ItemObject(" ", "resalat"));
        items.add(new ItemObject(" ", "noor"));

        return items;
    }

    String[] bankAddress = new String[]{
            "http://www.edbi.ir/Banking-Assistant-Deposits-Interest-Calculation/default.edbi",//"tosee_saderat"
            "http://www.ttbank.ir/calculator",              //"tosee_taavon"
            "http://www.postbank.ir/default-424.aspx",      //"post_bank"
            "http://www.bki.ir/BankingServices/DepositServices/%D9%85%D8%AD%D8%A7%D8%B3%D8%A8%D9%87_%D8%B3%D9%88%D8%AF_%D8%B3%D9%BE%D8%B1%D8%AF%D9%87-%D9%87%D8%A7",//"keshavarzi"
            "http://www.bim.ir/Rial-accounts/p4964500.bim", //"sanat_o_maadan"
            "http://www.banksepah.ir/default-1342.aspx",    //"sepah"
            "http://bmi.ir/Fa/perfitNEW.aspx?smnuid=54",    //"melli"
            "http://bank-maskan.ir/5097/index.aspx",        //"maskan"
            "http://www.cbi.ir/category/1611.aspx",         //"markazi"

            "http://www.ansarbank.com/Fa/Content/%D9%86%D8%B1%D8%AE-%D8%B3%D9%88%D8%AF-%D8%B9%D9%84%DB%8C-%D8%A7%D9%84%D8%AD%D8%B3%D8%A7%D8%A8-%D8%B3%D9%BE%D8%B1%D8%AF%D9%87-%D9%87%D8%A7-",//"ansar"
            "http://www.enbank.ir/Site.aspx?ParTree=1116111911",    //"eghtesade_novin"
            "https://www.ba24.ir/Fa/Benefit.aspx?tab=7&it=3",       //"ayande"
            "http://www.parsian-bank.com/Portal/Home/Default.aspx?CategoryID=0c121040-9a60-49d6-81af-f9d7995b272b", //"parsian"
            "http://www.ivbb.ir/search-result/p31197769-lksZ%D8%B3%D9%88%D8%AF+%D8%B3%D9%BE%D8%B1%D8%AF%D9%87.loco",//"iran_venezoela"
            "http://www.izbank.ir/calculation/func/loadmodule/system/calculation/sismodule/account___rial_account_benefit_calculation.php/sisOp/edit/",//"iran_zamin"
            "http://www.hibank24.ir/62",                            //"hekmate_iranian"
            "http://www.tejaratbank.ir/web_directory/1475-%D8%B3%D9%BE%D8%B1%D8%AF%D9%87.html",         //"tejarat"
            "http://www.bpi.ir/calcaccounts1",                      //"pasargad"
            "http://www.refah-bank.ir/portal/",                     //"refah"
            "http://www.bank-day.ir/index.aspx?siteid=1&fkeyid=&siteid=1&fkeyid=&siteid=1&pageid=662",  //"day"
            "http://www.middleeastbank.ir/index.aspx?fkeyid=&siteid=1&pageid=431",                      //"khavarmianeh"
            "http://www.sinabank.ir/calculation/func/loadmodule/system/calculation/sismodule/account___rial_account_benefit_calculation.php/sisOp/edit/",//"sina"
            "http://www.sbank.ir/calculation/func/loadmodule/system/calculation/sismodule/account___rial_account_benefit_calculation.php/sisOp/edit/",//"sarmayeh"
            "http://www.sb24.com/Fa/BankAssistant/sood-riali.html", //"saman"
            "http://www.ghbi.ir/interest_rate_on_deposits",         //"ghavamin"
            "http://www.bsi.ir/Pages/Riali/rialibenefit.aspx",      //"saderat"
            "http://shahr-bank.ir/index.aspx?fkeyid=&siteid=1&fkeyid=&siteid=1&pageid=1473",//"shahr"
            "http://www.bankmellat.ir/investment.aspx",             //"mellat"
            "http://www.tourismbank.ir/tab-450/default.aspx",       //"gardeshgari"
            "http://www.karafarinbank.ir/Deposits-Benefit-Rate/default.kb",                 //"karafarin"

            "http://www.kosarfci.ir/seporde",                   //"kosar"
            "http://www.askariyeh.ir/",                         //"askarieh"
            "http://www.cid.ir/tab-120/index.aspx",             //"tosee"

            "http://www.qmb.ir/",                               //"mehr"
            "http://rqbank.ir/",                                //"resalat"
            "http://www.cinoor.ir/"                             //"noor"

    };
}
