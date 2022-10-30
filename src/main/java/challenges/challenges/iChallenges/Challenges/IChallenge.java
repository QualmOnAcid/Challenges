package challenges.challenges.iChallenges.Challenges;

import challenges.challenges.iChallenges.Settings.ISettingConfig;

public interface IChallenge extends IChallengeConfig {
    void save();
    void load();
}