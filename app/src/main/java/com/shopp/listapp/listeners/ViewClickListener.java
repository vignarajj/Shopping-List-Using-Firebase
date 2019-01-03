package com.shopp.listapp.listeners;

import com.shopp.listapp.models.DataModel;

public interface ViewClickListener {
    void getData(DataModel model);
    void delete(DataModel model);
}
