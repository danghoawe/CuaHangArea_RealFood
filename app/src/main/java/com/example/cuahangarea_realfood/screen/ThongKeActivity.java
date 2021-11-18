package com.example.cuahangarea_realfood.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiDonHang;
import com.example.cuahangarea_realfood.model.DonHang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThongKeActivity extends AppCompatActivity {
    int choXacNhanCoc = 0, dangXuLy = 0, dangGiaoHang = 0, giaoHangThanhCong = 0, giaoHangThatBai = 0;
    String[] info = {"Đã nhận đơn", "Đang xử lý", "Đang giao hàng", "Giao hàng thành công", "Giao hàng thất bạt"};
    AnyChartView anyChartView;
    TextView txtTongSoDonHang,txtTongDoanhThu,txtDaGiaoThanhCong,txtChoChuyenCoc,txtDangXuLy,txtDangGiaoHang,txtGiaoHangKhongThanhCong;
    Firebase_Manager firebase_manager = new Firebase_Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        anyChartView = findViewById(R.id.piechartThongKe);
        txtTongSoDonHang = findViewById(R.id.txtTongDonHang);
        txtChoChuyenCoc = findViewById(R.id.txtChoChuyenCoc);
        txtDaGiaoThanhCong = findViewById(R.id.txtDaGiaoThanhCong);
        txtTongDoanhThu = findViewById(R.id.txtTongDoanhThu);
        txtDangXuLy = findViewById(R.id.txtDangXuLy);
        txtDangGiaoHang = findViewById(R.id.txtDangGiaoHang);
        txtGiaoHangKhongThanhCong = findViewById(R.id.txtGiaoHangKhongThanhCong);
        anyChartView.setProgressBar(findViewById(R.id.progessbar));

        GetThongKe_DonHang();

    }

    public void GetThongKe_DonHang() {
        choXacNhanCoc = 0;
        dangXuLy = 0;
        dangGiaoHang = 0;
        giaoHangThanhCong = 0;
        giaoHangThatBai = 0;
        firebase_manager.mDatabase.child("DonHang").orderByChild("idcuaHang").equalTo(firebase_manager.auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int tong =0;
               txtTongSoDonHang.setText( dataSnapshot.getChildrenCount()+"");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DonHang donHang = postSnapshot.getValue(DonHang.class);
                    if (donHang.getTrangThai() == TrangThaiDonHang.SHOP_ChoXacNhanChuyenTien) {
                        choXacNhanCoc++;
                    }
                    if (donHang.getTrangThai() == TrangThaiDonHang.SHOP_DaGiaoChoBep ||
                            donHang.getTrangThai() == TrangThaiDonHang.Bep_DaHuyDonHang ||
                            donHang.getTrangThai() == TrangThaiDonHang.SHOP_DangChuanBihang ||
                            donHang.getTrangThai() == TrangThaiDonHang.SHOP_DaChuanBiXong ||
                            donHang.getTrangThai() == TrangThaiDonHang.SHOP_DangGiaoShipper ||
                            donHang.getTrangThai() == TrangThaiDonHang.SHOP_ChoShipperLayHang ||
                            donHang.getTrangThai() == TrangThaiDonHang.Shipper_KhongNhanGiaoHang||
                            donHang.getTrangThai() == TrangThaiDonHang.SHOP_ChoXacNhanGiaoHangChoShipper) {
                        dangXuLy++;
                    }
                    if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaLayHang ||
                            donHang.getTrangThai() == TrangThaiDonHang.Shipper_DangGiaoHang ) {
                        dangGiaoHang++;
                    }
                    if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_GiaoKhongThanhCong ||
                            donHang.getTrangThai() == TrangThaiDonHang.ChoShopXacNhan_TraHang||
                            donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaTraHang||
                            donHang.getTrangThai() == TrangThaiDonHang.KhachHang_HuyDon||
                            donHang.getTrangThai() == TrangThaiDonHang.SHOP_HuyDonHang) {
                        giaoHangThatBai++;
                    }
                    if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_GiaoThanhCong ||
                            donHang.getTrangThai() == TrangThaiDonHang.ChoShopXacNhan_Tien||
                            donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaChuyenTien) {
                        giaoHangThanhCong++;
                        tong+=donHang.getTongTien();
                    }

                }
                LoadPieChart();
                txtTongDoanhThu.setText(tong+" VND");
                txtChoChuyenCoc.setText(choXacNhanCoc+"");
                txtDaGiaoThanhCong.setText(giaoHangThanhCong+"");
                txtDangGiaoHang.setText(dangGiaoHang+"");
                txtGiaoHangKhongThanhCong.setText(giaoHangThatBai+"");
                txtDangXuLy.setText(dangXuLy+"");
                Toast.makeText(ThongKeActivity.this, choXacNhanCoc + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void LoadPieChart() {
        Pie pie = AnyChart.pie();
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "Đơn hàng"}) {
            @Override
            public void onClick(Event event) {
            }
        });

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Chờ xác nhận cọc", choXacNhanCoc));
        data.add(new ValueDataEntry("Đang xử lí", dangXuLy));
        data.add(new ValueDataEntry("Đang giao hàng ", dangGiaoHang));
        data.add(new ValueDataEntry("Giao hàng thành công", giaoHangThanhCong));
        data.add(new ValueDataEntry("Giao hàng thất bại", giaoHangThatBai));
        pie.data(data);
        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Trạng thái đơn hàng")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }

}


