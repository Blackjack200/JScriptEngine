package com.blocklynukkit.loader.other.packets;

import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;

public class EducationSettingsPacket extends DataPacket {
    public String codeBuilderDefaultUri;
    public String codeBuilderTitle;
    public boolean canResizeCodeBuilder;
    public String codeBuilderOverrideUri = null;
    public boolean hasQuiz;

    @Override
    public byte pid() {
        return ProtocolInfo.EDUCATION_SETTINGS_PACKET;
    }

    @Override
    public void decode() {
        codeBuilderDefaultUri = this.getString();
        codeBuilderTitle = this.getString();
        canResizeCodeBuilder = this.getBoolean();
        if (this.getBoolean()) {
            codeBuilderOverrideUri = this.getString();
        }
        hasQuiz = this.getBoolean();
    }

    @Override
    public void encode() {
        this.putString(codeBuilderDefaultUri);
        this.putString(codeBuilderTitle);
        this.putBoolean(canResizeCodeBuilder);
        if (codeBuilderOverrideUri != null) {
            this.putString(codeBuilderOverrideUri);
        }
        this.putBoolean(hasQuiz);
    }

    public String getCodeBuilderDefaultUri() {
        return codeBuilderDefaultUri;
    }

    public void setCodeBuilderDefaultUri(String codeBuilderDefaultUri) {
        this.codeBuilderDefaultUri = codeBuilderDefaultUri;
    }

    public boolean isCanResizeCodeBuilder() {
        return canResizeCodeBuilder;
    }

    public void setCanResizeCodeBuilder(boolean canResizeCodeBuilder) {
        this.canResizeCodeBuilder = canResizeCodeBuilder;
    }

    public String getCodeBuilderTitle() {
        return codeBuilderTitle;
    }

    public void setCodeBuilderTitle(String codeBuilderTitle) {
        this.codeBuilderTitle = codeBuilderTitle;
    }

    public String getCodeBuilderOverrideUri() {
        return codeBuilderOverrideUri;
    }

    public void setCodeBuilderOverrideUri(String codeBuilderOverrideUri) {
        this.codeBuilderOverrideUri = codeBuilderOverrideUri;
    }

    public boolean isHasQuiz() {
        return hasQuiz;
    }

    public void setHasQuiz(boolean hasQuiz) {
        this.hasQuiz = hasQuiz;
    }

}
