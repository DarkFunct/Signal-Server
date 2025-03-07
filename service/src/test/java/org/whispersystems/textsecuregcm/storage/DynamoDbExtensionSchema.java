/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.whispersystems.textsecuregcm.storage;

import java.util.List;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.GlobalSecondaryIndex;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.LocalSecondaryIndex;
import software.amazon.awssdk.services.dynamodb.model.Projection;
import software.amazon.awssdk.services.dynamodb.model.ProjectionType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

public final class DynamoDbExtensionSchema {

  public enum Tables implements DynamoDbExtension.TableSchema {

    ACCOUNTS("accounts_test",
        Accounts.KEY_ACCOUNT_UUID,
        null,
        List.of(
            AttributeDefinition.builder()
                .attributeName(Accounts.KEY_ACCOUNT_UUID)
                .attributeType(ScalarAttributeType.B)
                .build(),
            AttributeDefinition.builder()
                .attributeName(Accounts.ATTR_USERNAME_LINK_UUID)
                .attributeType(ScalarAttributeType.B)
                .build()),
        List.of(
            GlobalSecondaryIndex.builder()
                .indexName(Accounts.USERNAME_LINK_TO_UUID_INDEX)
                .keySchema(
                    KeySchemaElement.builder()
                        .attributeName(Accounts.ATTR_USERNAME_LINK_UUID)
                        .keyType(KeyType.HASH)
                        .build()
                )
                .projection(Projection.builder().projectionType(ProjectionType.KEYS_ONLY).build())
                .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10L).writeCapacityUnits(10L).build())
                .build()
        ),
        List.of()),

    CLIENT_RELEASES("client_releases_test",
        ClientReleases.ATTR_PLATFORM,
        ClientReleases.ATTR_VERSION,
        List.of(
            AttributeDefinition.builder()
                .attributeName(ClientReleases.ATTR_PLATFORM)
                .attributeType(ScalarAttributeType.S)
                .build(),
            AttributeDefinition.builder()
                .attributeName(ClientReleases.ATTR_VERSION)
                .attributeType(ScalarAttributeType.S)
                .build()),
        List.of(),
        List.of()),

    DELETED_ACCOUNTS("deleted_accounts_test",
        Accounts.DELETED_ACCOUNTS_KEY_ACCOUNT_E164,
        null,
        List.of(
            AttributeDefinition.builder()
                .attributeName(Accounts.DELETED_ACCOUNTS_KEY_ACCOUNT_E164)
                .attributeType(ScalarAttributeType.S).build(),
            AttributeDefinition.builder()
                .attributeName(Accounts.DELETED_ACCOUNTS_ATTR_ACCOUNT_UUID)
                .attributeType(ScalarAttributeType.B)
                .build()),
        List.of(
            GlobalSecondaryIndex.builder()
                .indexName(Accounts.DELETED_ACCOUNTS_UUID_TO_E164_INDEX_NAME)
                .keySchema(
                    KeySchemaElement.builder().attributeName(Accounts.DELETED_ACCOUNTS_ATTR_ACCOUNT_UUID).keyType(KeyType.HASH).build()
                )
                .projection(Projection.builder().projectionType(ProjectionType.KEYS_ONLY).build())
                .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10L).writeCapacityUnits(10L).build())
                .build()),
        List.of()
    ),
  
    DELETED_ACCOUNTS_LOCK("deleted_accounts_lock_test",
        AccountLockManager.KEY_ACCOUNT_E164,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(AccountLockManager.KEY_ACCOUNT_E164)
            .attributeType(ScalarAttributeType.S).build()),
        List.of(), List.of()),
    
    NUMBERS("numbers_test",
        Accounts.ATTR_ACCOUNT_E164,
        null,
        List.of(AttributeDefinition.builder()
              .attributeName(Accounts.ATTR_ACCOUNT_E164)
              .attributeType(ScalarAttributeType.S)
            .build()),
        List.of(), List.of()),

    EC_KEYS("keys_test",
        SingleUsePreKeyStore.KEY_ACCOUNT_UUID,
        SingleUsePreKeyStore.KEY_DEVICE_ID_KEY_ID,
        List.of(
            AttributeDefinition.builder()
                .attributeName(SingleUsePreKeyStore.KEY_ACCOUNT_UUID)
                .attributeType(ScalarAttributeType.B)
                .build(),
            AttributeDefinition.builder()
                .attributeName(SingleUsePreKeyStore.KEY_DEVICE_ID_KEY_ID)
                .attributeType(ScalarAttributeType.B)
                .build()),
        List.of(), List.of()),

    PQ_KEYS("pq_keys_test",
        SingleUsePreKeyStore.KEY_ACCOUNT_UUID,
        SingleUsePreKeyStore.KEY_DEVICE_ID_KEY_ID,
        List.of(
            AttributeDefinition.builder()
                .attributeName(SingleUsePreKeyStore.KEY_ACCOUNT_UUID)
                .attributeType(ScalarAttributeType.B)
                .build(),
            AttributeDefinition.builder()
                .attributeName(SingleUsePreKeyStore.KEY_DEVICE_ID_KEY_ID)
                .attributeType(ScalarAttributeType.B)
                .build()),
        List.of(), List.of()),

    REPEATED_USE_EC_SIGNED_PRE_KEYS("repeated_use_signed_ec_pre_keys_test",
        RepeatedUseSignedPreKeyStore.KEY_ACCOUNT_UUID,
        RepeatedUseSignedPreKeyStore.KEY_DEVICE_ID,
        List.of(
            AttributeDefinition.builder()
                .attributeName(RepeatedUseSignedPreKeyStore.KEY_ACCOUNT_UUID)
                .attributeType(ScalarAttributeType.B)
                .build(),
            AttributeDefinition.builder()
                .attributeName(RepeatedUseSignedPreKeyStore.KEY_DEVICE_ID)
                .attributeType(ScalarAttributeType.N)
                .build()),
        List.of(), List.of()),

    REPEATED_USE_KEM_SIGNED_PRE_KEYS("repeated_use_signed_kem_pre_keys_test",
        RepeatedUseSignedPreKeyStore.KEY_ACCOUNT_UUID,
        RepeatedUseSignedPreKeyStore.KEY_DEVICE_ID,
        List.of(
            AttributeDefinition.builder()
                .attributeName(RepeatedUseSignedPreKeyStore.KEY_ACCOUNT_UUID)
                .attributeType(ScalarAttributeType.B)
                .build(),
            AttributeDefinition.builder()
                .attributeName(RepeatedUseSignedPreKeyStore.KEY_DEVICE_ID)
                .attributeType(ScalarAttributeType.N)
                .build()),
        List.of(), List.of()),

    PNI("pni_test",
        PhoneNumberIdentifiers.KEY_E164,
        null,
        List.of(
            AttributeDefinition.builder()
                .attributeName(PhoneNumberIdentifiers.KEY_E164)
                .attributeType(ScalarAttributeType.S)
                .build(),
            AttributeDefinition.builder()
                .attributeName(PhoneNumberIdentifiers.ATTR_PHONE_NUMBER_IDENTIFIER)
                .attributeType(ScalarAttributeType.B)
                .build()),
        List.of(GlobalSecondaryIndex.builder()
            .indexName(PhoneNumberIdentifiers.INDEX_NAME)
            .projection(Projection.builder()
                .projectionType(ProjectionType.KEYS_ONLY)
                .build())
            .keySchema(KeySchemaElement.builder().keyType(KeyType.HASH)
                .attributeName(PhoneNumberIdentifiers.ATTR_PHONE_NUMBER_IDENTIFIER)
                .build())
            .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10L).writeCapacityUnits(10L).build())
            .build()),
        List.of()),

    PNI_ASSIGNMENTS("pni_assignment_test",
        Accounts.ATTR_PNI_UUID,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(Accounts.ATTR_PNI_UUID)
            .attributeType(ScalarAttributeType.B)
            .build()),
        List.of(), List.of()),

    ISSUED_RECEIPTS("issued_receipts_test",
        IssuedReceiptsManager.KEY_PROCESSOR_ITEM_ID,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(IssuedReceiptsManager.KEY_PROCESSOR_ITEM_ID)
            .attributeType(ScalarAttributeType.S)
            .build()),
        List.of(), List.of()),

    MESSAGES("messages_test",
        MessagesDynamoDb.KEY_PARTITION,
        MessagesDynamoDb.KEY_SORT,
        List.of(
            AttributeDefinition.builder().attributeName(MessagesDynamoDb.KEY_PARTITION).attributeType(ScalarAttributeType.B).build(),
            AttributeDefinition.builder().attributeName(MessagesDynamoDb.KEY_SORT).attributeType(ScalarAttributeType.B).build(),
            AttributeDefinition.builder().attributeName(MessagesDynamoDb.LOCAL_INDEX_MESSAGE_UUID_KEY_SORT)
                .attributeType(ScalarAttributeType.B).build()),
        List.of(),
        List.of(LocalSecondaryIndex.builder()
            .indexName(MessagesDynamoDb.LOCAL_INDEX_MESSAGE_UUID_NAME)
            .keySchema(
                KeySchemaElement.builder().attributeName(MessagesDynamoDb.KEY_PARTITION).keyType(KeyType.HASH).build(),
                KeySchemaElement.builder()
                    .attributeName(MessagesDynamoDb.LOCAL_INDEX_MESSAGE_UUID_KEY_SORT)
                    .keyType(KeyType.RANGE)
                    .build())
            .projection(Projection.builder().projectionType(ProjectionType.KEYS_ONLY).build())
            .build())),

    PROFILES("profiles_test",
        Profiles.KEY_ACCOUNT_UUID,
        Profiles.ATTR_VERSION,
        List.of(
            AttributeDefinition.builder()
                .attributeName(Profiles.KEY_ACCOUNT_UUID)
                .attributeType(ScalarAttributeType.B)
                .build(),
            AttributeDefinition.builder()
                .attributeName(Profiles.ATTR_VERSION)
                .attributeType(ScalarAttributeType.S)
                .build()),
        List.of(), List.of()),
        
    PUSH_CHALLENGES("push_challenge_test",
        PushChallengeDynamoDb.KEY_ACCOUNT_UUID,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(PushChallengeDynamoDb.KEY_ACCOUNT_UUID)
            .attributeType(ScalarAttributeType.B)
            .build()),
        List.of(), List.of()),

    REDEEMED_RECEIPTS("redeemed_receipts_test",
        RedeemedReceiptsManager.KEY_SERIAL,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(RedeemedReceiptsManager.KEY_SERIAL)
            .attributeType(ScalarAttributeType.B)
            .build()),
        List.of(), List.of()),
  
    REGISTRATION_RECOVERY_PASSWORDS("registration_recovery_passwords_test",
        RegistrationRecoveryPasswords.KEY_E164,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(RegistrationRecoveryPasswords.KEY_E164)
            .attributeType(ScalarAttributeType.S)
            .build()),
        List.of(), List.of()),

    REMOTE_CONFIGS("remote_configs_test",
        RemoteConfigs.KEY_NAME,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(RemoteConfigs.KEY_NAME)
            .attributeType(ScalarAttributeType.S)
            .build()),
        List.of(), List.of()),

    REPORT_MESSAGES("report_messages_test",
        ReportMessageDynamoDb.KEY_HASH,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(ReportMessageDynamoDb.KEY_HASH)
            .attributeType(ScalarAttributeType.B)
            .build()),
        List.of(), List.of()),

    SUBSCRIPTIONS("subscriptions_test",
        SubscriptionManager.KEY_USER,
        null,
        List.of(
            AttributeDefinition.builder()
                .attributeName(SubscriptionManager.KEY_USER)
                .attributeType(ScalarAttributeType.B)
                .build(),
            AttributeDefinition.builder()
                .attributeName(SubscriptionManager.KEY_PROCESSOR_ID_CUSTOMER_ID)
                .attributeType(ScalarAttributeType.B)
                .build()),
        List.of(GlobalSecondaryIndex.builder()
            .indexName(SubscriptionManager.INDEX_NAME)
            .keySchema(KeySchemaElement.builder()
                .attributeName(SubscriptionManager.KEY_PROCESSOR_ID_CUSTOMER_ID)
                .keyType(KeyType.HASH)
                .build())
            .projection(Projection.builder()
                .projectionType(ProjectionType.KEYS_ONLY)
                .build())
            .provisionedThroughput(ProvisionedThroughput.builder()
                .readCapacityUnits(20L)
                .writeCapacityUnits(20L)
                .build())
            .build()),
        List.of()),

    USERNAMES("usernames_test",
        Accounts.ATTR_USERNAME_HASH,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(Accounts.ATTR_USERNAME_HASH)
            .attributeType(ScalarAttributeType.B)
            .build()),
        List.of(), List.of()),

    VERIFICATION_SESSIONS("verification_sessions_test",
        VerificationSessions.KEY_KEY,
        null,
        List.of(AttributeDefinition.builder()
            .attributeName(VerificationSessions.KEY_KEY)
            .attributeType(ScalarAttributeType.S)
            .build()),
        List.of(), List.of());

    private final String tableName;
    private final String hashKeyName;
    private final String rangeKeyName;
    private final List<AttributeDefinition> attributeDefinitions;
    private final List<GlobalSecondaryIndex> globalSecondaryIndexes;
    private final List<LocalSecondaryIndex> localSecondaryIndexes;

    Tables(
        final String tableName,
        final String hashKeyName,
        final String rangeKeyName,
        final List<AttributeDefinition> attributeDefinitions,
        final List<GlobalSecondaryIndex> globalSecondaryIndexes,
        final List<LocalSecondaryIndex> localSecondaryIndexes
    ) {
      this.tableName = tableName;
      this.hashKeyName = hashKeyName;
      this.rangeKeyName = rangeKeyName;
      this.attributeDefinitions = attributeDefinitions;
      this.globalSecondaryIndexes = globalSecondaryIndexes;
      this.localSecondaryIndexes = localSecondaryIndexes;
    }

    public String tableName() {
      return tableName;
    }

    public String hashKeyName() {
      return hashKeyName;
    }

    public String rangeKeyName() {
      return rangeKeyName;
    }

    public List<AttributeDefinition> attributeDefinitions() {
      return attributeDefinitions;
    }

    public List<GlobalSecondaryIndex> globalSecondaryIndexes() {
      return globalSecondaryIndexes;
    }

    public List<LocalSecondaryIndex> localSecondaryIndexes() {
      return localSecondaryIndexes;
    }

  }

}
