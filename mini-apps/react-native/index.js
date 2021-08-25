import React from 'react';
import { AppRegistry, StyleSheet, View, Text, Image } from 'react-native';

const App = (props) => {
  return (
    <View style={styles.container}>
      <Image source={require('./react-native.png')} resizeMode={'center'} style={{ width: 400, height: 100 }} />

      <View style={styles.idTokenContainer}>
        <Text style={styles.idTokenLabel}>Id Token:</Text>
        <Text>{props.terraIdToken}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  idTokenContainer: {
    margin: 16,
  },
  idTokenLabel: {
    fontWeight: 'bold',
    fontSize: 15,
    color: 'black',
  },
});

export default App;

// Module name
AppRegistry.registerComponent('RNMain', () => App);
