package com.programmergabut.solatkuy.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;


@SuppressWarnings("unused")
public class PermissionActivity extends Activity {


    private static final String PERMISSION = "PERMISSION";

    public static void contactsRead(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CONTACTS});
    }

    public static void contactsWrite(Context context) {
        checkGroup(context, new String[]{Manifest.permission.WRITE_CONTACTS});
    }

    public static void contactsRW(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS});
    }

    public static void calendarRead(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CALENDAR});
    }

    public static void calendarWrite(Context context) {
        checkGroup(context, new String[]{Manifest.permission.WRITE_CALENDAR});
    }

    public static void calendarRW(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR});
    }

    public static void storageRead(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    public static void storageWrite(Context context) {
        checkGroup(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    public static void storageRW(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    public static void locationFine(Context context) {
        checkGroup(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
    }

    public static void locationCoarse(Context context) {
        checkGroup(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    public static void locationBoth(Context context) {
        checkGroup(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    public static void camera(Context context) {
        checkGroup(context, new String[]{Manifest.permission.CAMERA});
    }

    public static void microphone(Context context) {
        checkGroup(context, new String[]{Manifest.permission.RECORD_AUDIO});
    }

    public static void phoneReadState(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_PHONE_STATE});
    }

    public static void phoneCall(Context context) {
        checkGroup(context, new String[]{Manifest.permission.CALL_PHONE});
    }

    public static void phoneReadCallLog(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CALL_LOG});
    }

    public static void phoneWriteCallLog(Context context) {
        checkGroup(context, new String[]{Manifest.permission.WRITE_CALL_LOG});
    }

    public static void phoneAddVoiceMail(Context context) {
        checkGroup(context, new String[]{Manifest.permission.ADD_VOICEMAIL});
    }

    public static void phoneSip(Context context) {
        checkGroup(context, new String[]{Manifest.permission.USE_SIP});
    }

    public static void phoneOutgoing(Context context) {
        checkGroup(context, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS});
    }

    public static void phoneAll(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.ADD_VOICEMAIL,
                Manifest.permission.USE_SIP,
                Manifest.permission.PROCESS_OUTGOING_CALLS});
    }

    public static void sensors(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            checkGroup(context, new String[]{Manifest.permission.BODY_SENSORS});
        }
    }

    public static void smsSend(Context context) {
        checkGroup(context, new String[]{Manifest.permission.SEND_SMS});
    }

    public static void smsReceive(Context context) {
        checkGroup(context, new String[]{Manifest.permission.RECEIVE_SMS});
    }

    public static void smsRead(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_SMS});
    }

    public static void smsWap(Context context) {
        checkGroup(context, new String[]{Manifest.permission.RECEIVE_WAP_PUSH});
    }

    public static void smsMms(Context context) {
        checkGroup(context, new String[]{Manifest.permission.RECEIVE_MMS});
    }

    public static void smsAll(Context context) {
        checkGroup(context, new String[]{Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_WAP_PUSH,
                Manifest.permission.RECEIVE_WAP_PUSH});
    }

    public static void checkGroup(Context context, String[] permissions) {
        if (permissions != null && permissions.length != 0) {
            Intent intent = new Intent(context, PermissionActivity.class);
            intent.putExtra(PERMISSION, permissions);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        String[] requestedPermissions = getRequestedPermissions();
        if (checkIsEmptyPermissions(requestedPermissions)) {
            int result = checkPermissions(requestedPermissions);
            requestPermissionsIfNeeded(result, requestedPermissions);
        } else {
            onPermissionDenied();
        }
    }

    private String[] getRequestedPermissions() {
        return getIntent().getExtras() != null ? getIntent().getExtras().getStringArray(PERMISSION) : null;
    }

    private boolean checkIsEmptyPermissions(String[] requestedPermissions) {
        return requestedPermissions != null && requestedPermissions.length != 0;
    }

    private void requestPermissionsIfNeeded(int result, String[] requestedPermissions) {
        if (result == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    requestedPermissions,
                    1001);
        } else {
            onPermissionGranted();
        }
    }

    private int checkPermissions(String[] requestedPermissions) {
        int result = 0;
        for (String permission : requestedPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                result = PackageManager.PERMISSION_DENIED;
                break;
            }
        }
        return result;
    }

    private void onPermissionDenied() {
        if (PermissionUtil.getCallback() != null) {
            PermissionUtil.getCallback().onPermissionDenied();
        }
        finish();
    }

    private void onPermissionGranted() {
        if (PermissionUtil.getCallback() != null) {
            PermissionUtil.getCallback().onPermissionGranted();
        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handleGrantResult(checkGrantResults(grantResults));
    }

    private void handleGrantResult(int grantResult) {
        if (grantResult == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted();
        } else {
            onPermissionDenied();
        }
    }

    private int checkGrantResults(int[] grantResults) {
        int result = 0;
        for (int grant : grantResults) {
            if (grant == PackageManager.PERMISSION_DENIED) {
                result = grant;
                break;
            }
        }
        return result;
    }

}
