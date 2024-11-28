package com.ccnu.src;

import com.ccnu.src.Drawable;

// Observer Pattern for Drawing Updates
interface DrawingListener {
    void onDraw(Drawable drawable);
}