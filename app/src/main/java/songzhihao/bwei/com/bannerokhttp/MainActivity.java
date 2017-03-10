package songzhihao.bwei.com.bannerokhttp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private XrecyclerViewAdapter xrecyclerViewAdapter;
    private XRecyclerView xrecyclerView;
    private String path = "http://api.fang.anjuke.com/m/android/1.3/shouye/recInfosV3/?city_id=14&lat=40.04652&lng=116.306033&api_key=androidkey&sig=9317e9634b5fbc16078ab07abb6661c5&macid=45cd2478331b184ff0e15f29aaa89e3e&app=a-ajk&_pid=11738&o=PE-TL10-user+4.4.2+HuaweiPE-TL10+CHNC00B260+ota-rel-keys%2Crelease-keys&from=mobile&m=Android-PE-TL10&cv=9.5.1&cid=14&i=864601026706713&v=4.4.2&pm=b61&uuid=1848c59c-185d-48d9-b0e9-782016041109&_chat_id=0&qtime=20160411091603";
    private ArrayList<Bean.ResultBean.RowsBean.InfoBean> lista;
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = new ArrayList<>();
        xrecyclerView = (XRecyclerView) findViewById(R.id.XRecyclerView);
        xrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        View view = View.inflate(MainActivity.this, R.layout.viewpager, null);
        xrecyclerView.addHeaderView(view);
        banner = (Banner) view.findViewById(R.id.banner);

        xrecyclerView.setLoadingMoreEnabled(true);
        getData(1);
        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            //刷新的方法
            @Override
            public void onRefresh() {
                path = "http://api.fang.anjuke.com/m/android/1.3/shouye/recInfosV3/?city_id=14&lat=40.04652&lng=116.306033&api_key=androidkey&sig=9317e9634b5fbc16078ab07abb6661c5&macid=45cd2478331b184ff0e15f29aaa89e3e&app=a-ajk&_pid=11738&o=PE-TL10-user+4.4.2+HuaweiPE-TL10+CHNC00B260+ota-rel-keys%2Crelease-keys&from=mobile&m=Android-PE-TL10&cv=9.5.1&cid=14&i=864601026706713&v=4.4.2&pm=b61&uuid=1848c59c-185d-48d9-b0e9-782016041109&_chat_id=0&qtime=20170211091603";
                getData(2);
            }
            //加载的方法
            @Override
            public void onLoadMore() {
                path = "http://api.fang.anjuke.com/m/android/1.3/shouye/recInfosV3/?city_id=14&lat=40.04652&lng=116.306033&api_key=androidkey&sig=9317e9634b5fbc16078ab07abb6661c5&macid=45cd2478331b184ff0e15f29aaa89e3e&app=a-ajk&_pid=11738&o=PE-TL10-user+4.4.2+HuaweiPE-TL10+CHNC00B260+ota-rel-keys%2Crelease-keys&from=mobile&m=Android-PE-TL10&cv=9.5.1&cid=14&i=864601026706713&v=4.4.2&pm=b61&uuid=1848c59c-185d-48d9-b0e9-782016041109&_chat_id=0&qtime=20160611091603";
                getData(3);
            }
        });

        ArrayList<String> list = new ArrayList<>();
        list.add("http://120.27.126.103:8080/ECServer_D/images/image1.png");
        list.add("http://120.27.126.103:8080/ECServer_D/images/image2.png");
        list.add("http://120.27.126.103:8080/ECServer_D/images/image3.png");
        list.add("http://120.27.126.103:8080/ECServer_D/images/image4.png");
        list.add("http://120.27.126.103:8080/ECServer_D/images/image5.png");
        //设置图片集合
        banner.setImages(list);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }

    public void getData(final int ll) {
        final OkHttputils httpUtils = OkHttputils.getHttpUtils();
        httpUtils.loadDataFromNet(path,Bean.class, new OkHttputils.CallBackListener<Bean>() {
            @Override
            public void onSuccess(Bean result) {
                List<Bean.ResultBean.RowsBean> rows = result.getResult().getRows();
                if (ll == 1) {
                    for (Bean.ResultBean.RowsBean a : rows) {
                        lista.add(a.getInfo());
                    }
                    xrecyclerViewAdapter = new XrecyclerViewAdapter(MainActivity.this,lista);
                    xrecyclerView.setAdapter(xrecyclerViewAdapter);
                }
                if (ll == 2){
                    lista.clear();
                    for (Bean.ResultBean.RowsBean aa : rows) {
                        lista.add(aa.getInfo());
                    }
                    xrecyclerView.refreshComplete();
                    xrecyclerViewAdapter.notifyDataSetChanged();
                }
                if (ll == 3){
                    for (Bean.ResultBean.RowsBean a : rows) {
                        lista.add(a.getInfo());
                    }
                    xrecyclerView.loadMoreComplete();
                    xrecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail() {

            }
        });

    }
}
