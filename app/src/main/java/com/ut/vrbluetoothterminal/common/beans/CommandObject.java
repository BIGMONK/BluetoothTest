// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: CommandObject.proto

package com.ut.vrbluetoothterminal.common.beans;

public final class CommandObject {
  private CommandObject() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface MessageObjectOrBuilder extends
      // @@protoc_insertion_point(interface_extends:MessageObject)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional string version = 1;</code>
     */
    String getVersion();
    /**
     * <code>optional string version = 1;</code>
     */
    com.google.protobuf.ByteString
        getVersionBytes();

    /**
     * <code>optional string command = 2;</code>
     */
    String getCommand();
    /**
     * <code>optional string command = 2;</code>
     */
    com.google.protobuf.ByteString
        getCommandBytes();

    /**
     * <code>map&lt;string, string&gt; params = 3;</code>
     */
    java.util.Map<String, String>
    getParams();

    /**
     * <code>optional string sequenceid = 4;</code>
     */
    String getSequenceid();
    /**
     * <code>optional string sequenceid = 4;</code>
     */
    com.google.protobuf.ByteString
        getSequenceidBytes();
  }
  /**
   * Protobuf type {@code MessageObject}
   */
  public  static final class MessageObject extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:MessageObject)
      MessageObjectOrBuilder {
    // Use MessageObject.newBuilder() to construct.
    private MessageObject(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private MessageObject() {
      version_ = "";
      command_ = "";
      sequenceid_ = "";
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private MessageObject(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              String s = input.readStringRequireUtf8();

              version_ = s;
              break;
            }
            case 18: {
              String s = input.readStringRequireUtf8();

              command_ = s;
              break;
            }
            case 26: {
              if (!((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
                params_ = com.google.protobuf.MapField.newMapField(
                    ParamsDefaultEntryHolder.defaultEntry);
                mutable_bitField0_ |= 0x00000004;
              }
              com.google.protobuf.MapEntry<String, String>
              params = input.readMessage(
                  ParamsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
              params_.getMutableMap().put(params.getKey(), params.getValue());
              break;
            }
            case 34: {
              String s = input.readStringRequireUtf8();

              sequenceid_ = s;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.ut.vrbluetoothterminal.common.beans.CommandObject.internal_static_MessageObject_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 3:
          return internalGetParams();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.ut.vrbluetoothterminal.common.beans.CommandObject.internal_static_MessageObject_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject.class, com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject.Builder.class);
    }

    private int bitField0_;
    public static final int VERSION_FIELD_NUMBER = 1;
    private volatile Object version_;
    /**
     * <code>optional string version = 1;</code>
     */
    public String getVersion() {
      Object ref = version_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        version_ = s;
        return s;
      }
    }
    /**
     * <code>optional string version = 1;</code>
     */
    public com.google.protobuf.ByteString
        getVersionBytes() {
      Object ref = version_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        version_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int COMMAND_FIELD_NUMBER = 2;
    private volatile Object command_;
    /**
     * <code>optional string command = 2;</code>
     */
    public String getCommand() {
      Object ref = command_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        command_ = s;
        return s;
      }
    }
    /**
     * <code>optional string command = 2;</code>
     */
    public com.google.protobuf.ByteString
        getCommandBytes() {
      Object ref = command_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        command_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PARAMS_FIELD_NUMBER = 3;
    private static final class ParamsDefaultEntryHolder {
      static final com.google.protobuf.MapEntry<
          String, String> defaultEntry =
              com.google.protobuf.MapEntry
              .<String, String>newDefaultInstance(
                  com.ut.vrbluetoothterminal.common.beans.CommandObject.internal_static_MessageObject_ParamsEntry_descriptor,
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "",
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "");
    }
    private com.google.protobuf.MapField<
        String, String> params_;
    private com.google.protobuf.MapField<String, String>
    internalGetParams() {
      if (params_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            ParamsDefaultEntryHolder.defaultEntry);
      }
      return params_;
    }
    /**
     * <code>map&lt;string, string&gt; params = 3;</code>
     */

    public java.util.Map<String, String> getParams() {
      return internalGetParams().getMap();
    }

    public static final int SEQUENCEID_FIELD_NUMBER = 4;
    private volatile Object sequenceid_;
    /**
     * <code>optional string sequenceid = 4;</code>
     */
    public String getSequenceid() {
      Object ref = sequenceid_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        sequenceid_ = s;
        return s;
      }
    }
    /**
     * <code>optional string sequenceid = 4;</code>
     */
    public com.google.protobuf.ByteString
        getSequenceidBytes() {
      Object ref = sequenceid_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        sequenceid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getVersionBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessage.writeString(output, 1, version_);
      }
      if (!getCommandBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessage.writeString(output, 2, command_);
      }
      for (java.util.Map.Entry<String, String> entry
           : internalGetParams().getMap().entrySet()) {
        com.google.protobuf.MapEntry<String, String>
        params = ParamsDefaultEntryHolder.defaultEntry.newBuilderForType()
            .setKey(entry.getKey())
            .setValue(entry.getValue())
            .build();
        output.writeMessage(3, params);
      }
      if (!getSequenceidBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessage.writeString(output, 4, sequenceid_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getVersionBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(1, version_);
      }
      if (!getCommandBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(2, command_);
      }
      for (java.util.Map.Entry<String, String> entry
           : internalGetParams().getMap().entrySet()) {
        com.google.protobuf.MapEntry<String, String>
        params = ParamsDefaultEntryHolder.defaultEntry.newBuilderForType()
            .setKey(entry.getKey())
            .setValue(entry.getValue())
            .build();
        size += com.google.protobuf.CodedOutputStream
            .computeMessageSize(3, params);
      }
      if (!getSequenceidBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(4, sequenceid_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code MessageObject}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:MessageObject)
        com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObjectOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.ut.vrbluetoothterminal.common.beans.CommandObject.internal_static_MessageObject_descriptor;
      }

      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMapField(
          int number) {
        switch (number) {
          case 3:
            return internalGetParams();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }
      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMutableMapField(
          int number) {
        switch (number) {
          case 3:
            return internalGetMutableParams();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.ut.vrbluetoothterminal.common.beans.CommandObject.internal_static_MessageObject_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject.class, com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject.Builder.class);
      }

      // Construct using com.ut.lightningsdk.common.beans.CommandObject.MessageObject.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        version_ = "";

        command_ = "";

        internalGetMutableParams().clear();
        sequenceid_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.ut.vrbluetoothterminal.common.beans.CommandObject.internal_static_MessageObject_descriptor;
      }

      public com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject getDefaultInstanceForType() {
        return com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject.getDefaultInstance();
      }

      public com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject build() {
        com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject buildPartial() {
        com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject result = new com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        result.version_ = version_;
        result.command_ = command_;
        result.params_ = internalGetParams();
        result.params_.makeImmutable();
        result.sequenceid_ = sequenceid_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject) {
          return mergeFrom((com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject other) {
        if (other == com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject.getDefaultInstance()) return this;
        if (!other.getVersion().isEmpty()) {
          version_ = other.version_;
          onChanged();
        }
        if (!other.getCommand().isEmpty()) {
          command_ = other.command_;
          onChanged();
        }
        internalGetMutableParams().mergeFrom(
            other.internalGetParams());
        if (!other.getSequenceid().isEmpty()) {
          sequenceid_ = other.sequenceid_;
          onChanged();
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private Object version_ = "";
      /**
       * <code>optional string version = 1;</code>
       */
      public String getVersion() {
        Object ref = version_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          version_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>optional string version = 1;</code>
       */
      public com.google.protobuf.ByteString
          getVersionBytes() {
        Object ref = version_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          version_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string version = 1;</code>
       */
      public Builder setVersion(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        version_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string version = 1;</code>
       */
      public Builder clearVersion() {
        
        version_ = getDefaultInstance().getVersion();
        onChanged();
        return this;
      }
      /**
       * <code>optional string version = 1;</code>
       */
      public Builder setVersionBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        version_ = value;
        onChanged();
        return this;
      }

      private Object command_ = "";
      /**
       * <code>optional string command = 2;</code>
       */
      public String getCommand() {
        Object ref = command_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          command_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>optional string command = 2;</code>
       */
      public com.google.protobuf.ByteString
          getCommandBytes() {
        Object ref = command_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          command_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string command = 2;</code>
       */
      public Builder setCommand(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        command_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string command = 2;</code>
       */
      public Builder clearCommand() {
        
        command_ = getDefaultInstance().getCommand();
        onChanged();
        return this;
      }
      /**
       * <code>optional string command = 2;</code>
       */
      public Builder setCommandBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        command_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.MapField<
          String, String> params_;
      private com.google.protobuf.MapField<String, String>
      internalGetParams() {
        if (params_ == null) {
          return com.google.protobuf.MapField.emptyMapField(
              ParamsDefaultEntryHolder.defaultEntry);
        }
        return params_;
      }
      private com.google.protobuf.MapField<String, String>
      internalGetMutableParams() {
        onChanged();;
        if (params_ == null) {
          params_ = com.google.protobuf.MapField.newMapField(
              ParamsDefaultEntryHolder.defaultEntry);
        }
        if (!params_.isMutable()) {
          params_ = params_.copy();
        }
        return params_;
      }
      /**
       * <code>map&lt;string, string&gt; params = 3;</code>
       */
      public java.util.Map<String, String> getParams() {
        return internalGetParams().getMap();
      }
      /**
       * <code>map&lt;string, string&gt; params = 3;</code>
       */
      public java.util.Map<String, String>
      getMutableParams() {
        return internalGetMutableParams().getMutableMap();
      }
      /**
       * <code>map&lt;string, string&gt; params = 3;</code>
       */
      public Builder putAllParams(
          java.util.Map<String, String> values) {
        getMutableParams().putAll(values);
        return this;
      }

      private Object sequenceid_ = "";
      /**
       * <code>optional string sequenceid = 4;</code>
       */
      public String getSequenceid() {
        Object ref = sequenceid_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          sequenceid_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>optional string sequenceid = 4;</code>
       */
      public com.google.protobuf.ByteString
          getSequenceidBytes() {
        Object ref = sequenceid_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          sequenceid_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string sequenceid = 4;</code>
       */
      public Builder setSequenceid(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        sequenceid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string sequenceid = 4;</code>
       */
      public Builder clearSequenceid() {
        
        sequenceid_ = getDefaultInstance().getSequenceid();
        onChanged();
        return this;
      }
      /**
       * <code>optional string sequenceid = 4;</code>
       */
      public Builder setSequenceidBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        sequenceid_ = value;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:MessageObject)
    }

    // @@protoc_insertion_point(class_scope:MessageObject)
    private static final com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject();
    }

    public static com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MessageObject>
        PARSER = new com.google.protobuf.AbstractParser<MessageObject>() {
      public MessageObject parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new MessageObject(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MessageObject> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<MessageObject> getParserForType() {
      return PARSER;
    }

    public com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MessageObject_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_MessageObject_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MessageObject_ParamsEntry_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_MessageObject_ParamsEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\023CommandObject.proto\"\240\001\n\rMessageObject\022" +
      "\017\n\007version\030\001 \001(\t\022\017\n\007command\030\002 \001(\t\022*\n\006par" +
      "ams\030\003 \003(\0132\032.MessageObject.ParamsEntry\022\022\n" +
      "\nsequenceid\030\004 \001(\t\032-\n\013ParamsEntry\022\013\n\003key\030" +
      "\001 \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001B\"\n com.ut.light" +
      "ningsdk.common.beansb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_MessageObject_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_MessageObject_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_MessageObject_descriptor,
        new String[] { "Version", "Command", "Params", "Sequenceid", });
    internal_static_MessageObject_ParamsEntry_descriptor =
      internal_static_MessageObject_descriptor.getNestedTypes().get(0);
    internal_static_MessageObject_ParamsEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_MessageObject_ParamsEntry_descriptor,
        new String[] { "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}